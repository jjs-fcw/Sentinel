package com.alibaba.csp.sentinel.dashboard.datasource.entity.user;

import java.io.Serializable;

/**
 * Created by @author:zhengxgs on 2021/4/14.
 */
public class UserPermissions implements Serializable {

    private Integer id;

    /**
     * 登录账号
     */
    private String userName;

    /**
     * 对应工号
     */
    private String workerId;
    /**
     * 应用名称
     */
    private String app;

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

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}