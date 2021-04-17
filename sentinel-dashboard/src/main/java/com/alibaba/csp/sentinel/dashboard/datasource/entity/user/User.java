package com.alibaba.csp.sentinel.dashboard.datasource.entity.user;

import com.gitee.baa.annotation.Table;
import java.io.Serializable;

/**
 * Created by @author:zhengxgs on 2021/4/14.
 */
@Table("sentinel_user")
public class User implements Serializable {

    private Integer id;
    private String userName;
    private String userPwd;
    private String nickName;

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
}