package com.alibaba.csp.sentinel.dashboard.rule.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author:zhengxgs on 2021/1/23.
 */
@Configuration
public class MemoryConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "persistentApiClient")
    public MemoryApiClient persistentApiClient(){
        return new MemoryApiClient();
    }
}
