-- for mysql
-- 生成指定范围的随机正整数，包括入参中的最大、最小值

delimiter $$
CREATE FUNCTION rand_range_num(min int, max int) RETURNS INT
BEGIN
    DECLARE return_num int DEFAULT 0;

    set return_num = min + CEIL(rand() * (max - min + 1));
    RETURN return_num;
END $$