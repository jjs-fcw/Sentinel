package com.alibaba.csp.sentinel.dashboard.domain.vo.user;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.user.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by @author:zhengxgs on 2021/4/15.
 */
public class UserVo implements Serializable {

    private Integer id;
    private String userName;
    private String userPwd;
    private String nickName;
    private String apps;

    public static List<UserVo> fromUserList(List<User> users) {
        List<UserVo> list = new ArrayList<>();
        for (User user : users) {
            list.add(fromUser(user));
        }
        return list;
    }

    public static UserVo fromUser(User user) {
        UserVo vo = new UserVo();
        vo.setId(user.getId());
        vo.setUserName(user.getUserName());
        vo.setUserPwd(user.getUserPwd());
        vo.setNickName(user.getNickName());
        return vo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getApps() {
        return apps;
    }

    public void setApps(String apps) {
        this.apps = apps;
    }
}