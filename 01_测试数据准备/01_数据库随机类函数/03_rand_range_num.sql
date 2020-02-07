-- for mysql
-- 说明：生成指定范围的随机正整数，包括入参中的最大、最小值
-- 使用例子： select rand_range_num(1000, 9999); -- 输出 1541

delimiter $$
CREATE FUNCTION rand_range_num(min int, max int) RETURNS INT  NO SQL
BEGIN
    DECLARE return_num int DEFAULT 0;

    set return_num = min + CEIL(rand() * (max - min + 1));
    RETURN return_num;
END $$