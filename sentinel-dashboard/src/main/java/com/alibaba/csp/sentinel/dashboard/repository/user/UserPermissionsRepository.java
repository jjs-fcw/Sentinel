package com.alibaba.csp.sentinel.dashboard.repository.user;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.user.UserPermissions;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by @author:zhengxgs on 2021/4/14.
 */
@Transactional
@Repository("userPermissionsRepository")
public class UserPermissionsRepository extends UserPermissionsRepositoryAdapter<UserPermissions> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(UserPermissions userPermissions) {
        String sql = "insert into sentinel_permissions(user_name,worker_id,app) values (?,?,?)";
        jdbcTemplate.update(sql, userPermissions.getUserName(), userPermissions.getWorkerId(), userPermissions.getApp());
    }

    @Override
    public void saveAll(Iterable<UserPermissions> t) {
        t.forEach(this::save);
    }

    @Override
    public UserPermissions select(Integer id) {
        try {
            return jdbcTemplate
                .queryForObject("select * from sentinel_permissions where id = ?", new BeanPropertyRowMapper<>(UserPermissions.class), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<UserPermissions> selectList(UserPermissions permissions) {
        List<Object> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select * from sentinel_permissions where 1=1");
        if (permissions.getUserName() != null) {
            sql.append(" and user_name=?");
            list.add(permissions.getUserName());
        }
        return this.jdbcTemplate.query(sql.toString(), list.toArray(), (resultSet, i) -> {
            UserPermissions userPermissions = new UserPermissions();
            userPermissions.setId(resultSet.getInt("id"));
            userPermissions.setUserName(resultSet.getString("user_name"));
            userPermissions.setWorkerId(resultSet.getString("worker_id"));
            userPermissions.setApp(resultSet.getString("app"));
            return userPermissions;
        });
    }

    @Override
    public void update(UserPermissions permissions) {
        String sql = "update sentinel_permissions set user_name=?, worker_id=?, app=? where id=?";
        this.jdbcTemplate.update(sql, permissions.getUserName(), permissions.getWorkerId(), permissions.getApp(), permissions.getId());
    }

    @Override
    public int deleteByUserName(String userName) {
        String sql = "delete from sentinel_permissions where user_name=?";
        return this.jdbcTemplate.update(sql, userName);
    }
}