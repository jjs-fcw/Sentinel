package com.alibaba.csp.sentinel.dashboard.repository.user;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.user.User;

/**
 * Created by @author:zhengxgs on 2021/4/14.
 */
public abstract class UserRepositoryAdapter<T extends User> implements JpaRepository<T, Integer> {

    abstract public int delete(Integer id);

    abstract public User selectByUserName(String userName);

}