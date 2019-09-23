-- for mysql
-- 创建用于测试的数据库表

CREATE TABLE `account` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(128) NOT NULL COMMENT '姓名',
  `pwd` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(64) NOT NULL COMMENT '密码加盐',
  `gender` int(1) DEFAULT '0' COMMENT '性别（0：未知。1：男。2：女）',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `state` int(1) DEFAULT 1 COMMENT '用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `uid` varchar(255) DEFAULT NULL COMMENT 'uid',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;