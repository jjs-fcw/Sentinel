package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.RuleConfigTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by @author:zhengxgs on 2021/1/23.
 */
public abstract class AbstractpersistentRuleApiClient<T> implements PersistentRuleApiClient<T> {

    private static Logger logger = LoggerFactory.getLogger(AbstractpersistentRuleApiClient.class);

    @Override
    public boolean publishReturnBoolean(String app, RuleConfigTypeEnum configType, List<T> rules) {
        try {
            this.publish(app, configType, rules);
        } catch (Exception ex) {
            logger.warn("setRules API failed: {}", configType.getValue(), ex);
            return false;
        }
        return true;
    }

}
