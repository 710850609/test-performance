-- for mysql
-- 这里以用户表基础数据新增为例
-- 使用到 01_测试数据准备\01_数据库随机类函数 中的数据库函数
-- 用户表的DML如下：
-- CREATE TABLE `account` (
--   `id` int(12) NOT NULL AUTO_INCREMENT,
--   `name` varchar(128) NOT NULL COMMENT '姓名',
--   `pwd` varchar(255) NOT NULL COMMENT '密码',
--   `salt` varchar(64) NOT NULL COMMENT '密码加盐',
--   `gender` int(1) DEFAULT '0' COMMENT '性别（0：未知。1：男。2：女）',
--   `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
--   `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
--   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
--   `update_time` datetime DEFAULT NULL COMMENT '修改时间',
--   `uid` varchar(255) DEFAULT NULL COMMENT 'uid',
--   `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
--   PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

delimiter $$
CREATE PROCEDURE proc_insert_account(in batch_num int(12))
BEGIN
    DECLARE i INT DEFAULT 0;
    SET autocommit = 1;
    repeat
        set i = i + 1;
        insert into account(`id`, `name`, pwd, salt, gender, mobile, email, create_time, update_time, `uid`, last_login_time) values 
        (null, rand_chinese_name(), rand_str(8), rand_str(6), null, rand_mobile(), null, rand_date('2010-01-01 00:00:00', now()), null, replace(uuid(), '-', ''), null);
    until i = batch_num
    END repeat;
END $$