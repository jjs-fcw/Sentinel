package com.alibaba.csp.sentinel.dashboard.controller;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.auth.AuthService.AuthUser;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.user.User;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.user.UserPermissions;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.domain.vo.user.UserVo;
import com.alibaba.csp.sentinel.dashboard.repository.user.UserPermissionsRepositoryAdapter;
import com.alibaba.csp.sentinel.dashboard.repository.user.UserRepositoryAdapter;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author:zhengxgs on 2021/4/15.
 */
@RestController
@RequestMapping("/quanxian")
public class QuanxianController {

    private final Logger logger = LoggerFactory.getLogger(QuanxianController.class);

    @Value("${admin.username:admin}")
    private String adminUsername;

    @Autowired
    private AuthService<HttpServletRequest> authService;
    @Autowired
    private UserRepositoryAdapter<User> userStore;
    @Autowired
    private UserPermissionsRepositoryAdapter<UserPermissions> permissionsStore;

    @GetMapping("/user.json")
    public Result<List<UserVo>> apiQueryUsers(HttpServletRequest request) {
        Result<List<UserVo>> checkResult = checkAdmin(request);
        if (checkResult != null) {
            return checkResult;
        }
        try {
            User query = new User();
            List<User> users = userStore.selectList(query);
            List<UserVo> result = UserVo.fromUserList(users);
            result.stream().forEach(u -> {
                UserPermissions queryBean = new UserPermissions();
                queryBean.setUserName(u.getUserName());
                List<UserPermissions> userPermissions = permissionsStore.selectList(queryBean);
                String apps = userPermissions.stream().map(UserPermissions::getApp).collect(Collectors.joining(","));
                u.setApps(apps);
            });
            return Result.ofSuccess(result);
        } catch (Throwable throwable) {
            return Result.ofThrowable(-1, throwable);
        }
    }

    @PostMapping("/user")
    public Result<UserVo> apiAddUser(@RequestBody UserVo entity, HttpServletRequest request) {
        Result<UserVo> checkResult = checkAdmin(request);
        if (checkResult != null) {
            return checkResult;
        }
        try {
            User user = new User();
            user.setUserName(entity.getUserName());
            user.setPwd(entity.getPwd());
            user.setNickName(entity.getNickName());
            userStore.save(user);
            permissionsStore.deleteByUserName(entity.getUserName());
            for (String app : entity.getApps().split(",")) {
                UserPermissions userPermissions = new UserPermissions();
                userPermissions.setApp(app);
                userPermissions.setUserName(entity.getUserName());
                permissionsStore.save(userPermissions);
            }
        } catch (Throwable t) {
            logger.error("Failed to add new user", t);
            return Result.ofThrowable(-1, t);
        }
        return Result.ofSuccess(entity);
    }

    @PutMapping("/user/{id}")
    public Result<UserVo> apiUpdateUser(@PathVariable("id") Integer id, @RequestBody UserVo entity, HttpServletRequest request) {
        Result<UserVo> checkResult = checkAdmin(request);
        if (checkResult != null) {
            return checkResult;
        }
        if (id == null || id <= 0) {
            return Result.ofFail(-1, "id can't be null or negative");
        }
        try {
            User user = new User();
            user.setId(entity.getId());
            user.setUserName(entity.getUserName());
            user.setPwd(entity.getPwd());
            user.setNickName(entity.getNickName());
            userStore.update(user);

            permissionsStore.deleteByUserName(entity.getUserName());
            for (String app : entity.getApps().split(",")) {
                UserPermissions userPermissions = new UserPermissions();
                userPermissions.setApp(app);
                userPermissions.setUserName(entity.getUserName());
                permissionsStore.save(userPermissions);
            }
        } catch (Throwable t) {
            logger.error("Failed to save user, id={}, user={}", id, entity, t);
            return Result.ofThrowable(-1, t);
        }
        return Result.ofSuccess(entity);
    }

    @DeleteMapping("/user/{id}")
    public Result<Integer> apiDeleteUser(@PathVariable("id") Integer id, HttpServletRequest request) {
        Result<Integer> checkResult = checkAdmin(request);
        if (checkResult != null) {
            return checkResult;
        }
        if (id == null) {
            return Result.ofFail(-1, "params can't be null");
        }
        User oldEntity = userStore.select(id);
        if (oldEntity == null) {
            return Result.ofFail(-1, "old entity can't be null");
        }
        try {
            userStore.delete(id);
            permissionsStore.deleteByUserName(oldEntity.getUserName());
        } catch (Throwable throwable) {
            logger.error("Failed to delete user, id={}", id, throwable);
            return Result.ofThrowable(-1, throwable);
        }
        return Result.ofSuccess(id);
    }

    private <R> Result<R> checkAdmin(HttpServletRequest request) {
        AuthUser authUser = authService.getAuthUser(request);
        if (authUser == null) {
            return Result.ofFail(-1, "forbidden");
        }
        if (!adminUsername.equals(authUser.getNickName())) {
            return Result.ofFail(-1, "forbidden");
        }
        return null;
    }
}