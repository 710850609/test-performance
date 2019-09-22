-- for mysql
-- 生成固定长度的随机整数

delimiter $$
create function rand_fixed_num(num int) returns int
begin
    DECLARE i INT DEFAULT 0;
    DECLARE return_num INT DEFAULT 0;
    WHILE i < num DO
        -- 生成0-9范围的随机数
        SET return_num = return_num * 10 + CEIL(rand() * 10);
        IF (i > 0 or (i = 0 and return_num > 0)) THEN
            SET i = i + 1;
        end IF;
    END WHILE;
    RETURN return_num;
end $$
