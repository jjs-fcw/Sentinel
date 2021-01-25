package com.alibaba.csp.sentinel.dashboard.datasource;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayParamFlowItemEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;

/**
 * keys of rule seetings stored in config center
 * Created by @author:zhengxgs on 2021/1/23.
 */
public enum RuleConfigTypeEnum {

    FLOW("flow-rules", FlowRuleEntity.class),
    PARAM_FLOW("param-rules", ParamFlowRuleEntity.class),
    AUTHORITY("authority-rules", AuthorityRuleEntity.class),
    DEGRADE("degrade-rules", DegradeRuleEntity.class),
    SYSTEM("system-rules", SystemRuleEntity.class),
    GATEWAY_FLOW("gateway-flow-rules", GatewayFlowRuleEntity.class),
    GATEWAY_PARAM_FLOW("gateway-param-flow-rules", GatewayParamFlowItemEntity.class);

//    CLUSTER("sentinel-cluster-map"),
//    CLIENT_CONFIG("sentinel-cluster-client-config"),

//    SERVER_TRANSPORT_CONFIG("sentinel-cluster-server-transport-config"),
//    SERVER_FLOW_CONFIG("sentinel-cluster-server-flow-config"),
//    SERVER_NAMESPACE_SET("sentinel-cluster-server-namespace-set");

    private String value;
    private Class clazz;

    RuleConfigTypeEnum(String value, Class clazz){
        this.value = value;
        this.clazz = clazz;
    }

    public String getValue(){
        return this.value;
    }

    public Class getClazz() {
        return this.clazz;
    }

}
