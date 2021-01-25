package com.alibaba.csp.sentinel.dashboard.rule.zookeeper;

import com.alibaba.csp.sentinel.dashboard.datasource.RuleConfigTypeEnum;
import com.alibaba.csp.sentinel.dashboard.rule.AbstractpersistentRuleApiClient;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by @author:zhengxgs on 2021/1/23.
 */
public class ZookeeperApiClient<T> extends AbstractpersistentRuleApiClient<T> {

    @Autowired
    private CuratorFramework zkClient;

    public String getRuleConfigId(String appName, RuleConfigTypeEnum ruleFix) {
        return String.format("/sentinel/%s/%s-%s", appName, appName, ruleFix.getValue());
    }

    @Override
    public List<T> fetch(String app, RuleConfigTypeEnum configType) throws Exception {
        String zkPath = this.getRuleConfigId(app, configType);
        Stat stat = zkClient.checkExists().forPath(zkPath);
        if(stat == null){
            return (List<T>) new ArrayList(0);
        }
        byte[] bytes = zkClient.getData().forPath(zkPath);
        if (null == bytes || bytes.length == 0) {
            return (List<T>) new ArrayList();
        }
        String rulesJson = new String(bytes);

        return JSON.parseArray(rulesJson, configType.getClazz());
    }


    @Override
    public void publish(String app, RuleConfigTypeEnum configType, List<T> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");

        String path = this.getRuleConfigId(app, configType);
        Stat stat = zkClient.checkExists().forPath(path);
        if (stat == null) {
            zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, null);
        }

        byte[] data = (rules == null || CollectionUtils.isEmpty((Collection<?>) rules)) ? "[]".getBytes() : JSON.toJSONString(rules,true).getBytes();
        zkClient.setData().forPath(path, data);
    }
}
