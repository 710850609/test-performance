-- for mysql
-- 说明： 生成随机IP
-- 使用例子： select rand_ip(); -- 输出 151.195.16.184

delimiter $$
CREATE FUNCTION rand_ip() RETURNS VARCHAR(15) CHARSET utf8 NO SQL
BEGIN
    -- 生成第一网段
    SET @tmp_ip_1 = rand_range_num(127, 254);
    -- 生成第二网段
    SET @tmp_ip_2 = rand_range_num(127, 254);
    -- 生成第三网段
    SET @tmp_ip_3 = rand_range_num(1, 254);
    -- 生成第四网段
    SET @tmp_ip_4 = rand_range_num(1, 254);
    return concat(@tmp_ip_1, '.', @tmp_ip_2, '.', @tmp_ip_3, '.', @tmp_ip_4);
END $$