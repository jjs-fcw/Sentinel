package com.alibaba.csp.sentinel.dashboard.repository.user;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.user.User;
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
@Repository("userRepository")
public class UserRepository extends UserRepositoryAdapter<User> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        String sql = "insert into sentinel_user(user_name,user_pwd,nick_name) values (?,?,?)";
        jdbcTemplate.update(sql, user.getUserName(), user.getUserPwd(), user.getNickName());
    }

    @Override
    public void saveAll(Iterable<User> t) {
        t.forEach(this::save);
    }

    @Override
    public User select(Integer id) {
//        User user = new User();
//        String sql="select * from sentinel_user where id = ?";
//        Object[] arr = new Object[]{id};
//        this.jdbcTemplate.query(sql, arr, resultSet -> {
//            user.setId(resultSet.getInt("id"));
//            user.setUserName(resultSet.getString("user_name"));
//            user.setPwd(resultSet.getString("user_pwd"));
//            user.setNickName(resultSet.getString("nick_name"));
//        });
//        if (user.getId() != null) {
//            return user;
//        }
//        return null;
        try {
            return jdbcTemplate.queryForObject("select * from sentinel_user where id = ?", new BeanPropertyRowMapper<>(User.class), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> selectList(User query) {
        return jdbcTemplate.query("select * from sentinel_user", new BeanPropertyRowMapper(User.class));
    }

    @Override
    public void update(User user) {
        String sql = "update sentinel_user set user_name=?, user_pwd=?, nick_name=? where id=?";
        this.jdbcTemplate.update(sql, user.getUserName(), user.getUserPwd(), user.getNickName(), user.getId());
    }

    @Override
    public int delete(Integer id) {
        String sql = "delete from sentinel_user where id=?";
        return this.jdbcTemplate.update(sql, id);
    }

    @Override
    public User selectByUserName(String userName) {
        try {
            return jdbcTemplate.queryForObject("select * from sentinel_user where user_name = ?", new BeanPropertyRowMapper<>(User.class), userName);
        } catch (DataAccessException e) {
            return null;
        }
    }
}