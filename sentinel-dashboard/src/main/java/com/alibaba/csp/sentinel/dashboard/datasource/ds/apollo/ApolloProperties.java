package com.alibaba.csp.sentinel.dashboard.datasource.ds.apollo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by @author:zhengxgs on 2021/1/23.
 */
@Component
public class ApolloProperties {

    @Value("${datasource.provider.apollo.server-addr:http://localhost:10034}")
    private String serverAddr;

    @Value("${datasource.provider.apollo.token:token}")
    private String token;


    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("Datasource.ApolloProperties ");
        builder.append("[ ");
        builder.append(  "serverAddr").append("=").append(serverAddr).append(",");
        builder.append(  "token").append("=").append(token).append(",");
        builder.append("] ");

        return  builder.toString();
    }

}
