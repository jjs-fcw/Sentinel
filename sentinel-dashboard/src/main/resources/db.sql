CREATE TABLE `sentinel_user` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
    `user_pwd` varchar(30) DEFAULT NULL COMMENT '密码',
    `user_name` varchar(30) DEFAULT NULL COMMENT '账号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='账号表';


CREATE TABLE `sentinel_permissions` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `worker_id` varchar(20) DEFAULT NULL COMMENT 'workerId',
    `app` varchar(30) DEFAULT NULL COMMENT '拥有的应用名称',
    `user_name` varchar(30) DEFAULT NULL COMMENT '账号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='用户权限';

