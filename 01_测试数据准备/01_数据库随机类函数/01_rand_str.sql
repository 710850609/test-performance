-- for mysql
-- 说明： 生成固定长度随机字符串
-- 使用例子： select rand_str(5); -- 输出 Mal23W

delimiter $$
create function rand_str(num int) returns varchar(255) charset 'utf8'
begin
    DECLARE chars_str varchar(62) default 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    DECLARE return_str varchar(255) DEFAULT '';
    DECLARE i INT DEFAULT 0;
    WHILE i < num DO
        SET return_str = concat(return_str, substring(chars_str, floor(1 + rand()*62), 1));
        SET i = i + 1;
    END WHILE;
    RETURN return_str;
end $$