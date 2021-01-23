package com.alibaba.csp.sentinel.dashboard.rule.nacos;


import com.alibaba.csp.sentinel.dashboard.rule.PersistentRuleApiClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author:zhengxgs on 2021/1/23.
 */
@Configuration
@ConditionalOnBean(name = "nacosConfigService")
public class NacosApiClientConfiguration {

    @Bean
    public PersistentRuleApiClient persistentApiClient(){
        return new NacosApiClient();
    }

}
