package com.alibaba.csp.sentinel.dashboard.repository.user;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.user.UserPermissions;

/**
 * Created by @author:zhengxgs on 2021/4/14.
 */
public abstract class UserPermissionsRepositoryAdapter<T extends UserPermissions> implements JpaRepository<T, Integer> {

    abstract public int deleteByUserName(String userName);
}