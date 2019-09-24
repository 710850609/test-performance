-- for mysql
-- 创建用于测试的数据库表

CREATE TABLE `account` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT '姓名',
  `pwd` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(64) NOT NULL COMMENT '密码加盐',
  `gender` int(1) DEFAULT '0' COMMENT '性别（0：未知。1：男。2：女）',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `state` int(1) DEFAULT '1' COMMENT '状态（0：禁用。1：正常）',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `id_card_no` varchar(18) DEFAULT NULL COMMENT '身份证号码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `uid` varchar(255) DEFAULT NULL COMMENT 'uid',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(12) DEFAULT NULL COMMENT '最后登录IP',
  `error_login_count` int(2) DEFAULT NULL COMMENT '连续登录错误次数',
  `login_count` int(12) DEFAULT NULL COMMENT '登录次数',
  PRIMARY KEY (`id`),
  KEY `mobile` (`mobile`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;