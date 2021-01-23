package com.alibaba.csp.sentinel.dashboard.datasource.ds;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by @author:zhengxgs on 2021/1/23.
 */
@Component
public class DataSourceProperties {

    public static final String PREFIX_DATASOURCE = "datasource";
    public static final String NAME_PROVIDER = "provider";

    public static final String VALUE_PROVIDER_MEMORY = "memory";
    public static final String VALUE_PROVIDER_APOLLO = "apollo";
    public static final String VALUE_PROVIDER_NACOS = "nacos";
    public static final String VALUE_PROVIDER_ZOOKEEPER = "zookeeper";

    @Value("${datasource.provider:memory}")
    private String provider;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
