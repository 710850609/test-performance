-- for mysql
-- 说明： 生成随机手机号
-- 使用例子： select rand_mobile(); -- 输出 17680812643

delimiter $$
CREATE FUNCTION rand_mobile() RETURNS char(11) CHARSET utf8
BEGIN
    DECLARE tmp_head VARCHAR(100) DEFAULT '158,156,136,176';
    DECLARE tmp_content CHAR(10) DEFAULT '0123456789';
    DECLARE return_phone CHAR(11) DEFAULT substring(tmp_head, 1+(FLOOR(1 + (RAND() * 3))*4), 3);
      
    DECLARE i int DEFAULT 1;
    DECLARE len int DEFAULT LENGTH(tmp_content);
    WHILE i<9 DO
        SET i = i + 1;
        SET return_phone = CONCAT(return_phone, substring(tmp_content, floor(1 + RAND() * len), 1));
    END WHILE;
    
    RETURN return_phone;
END $$