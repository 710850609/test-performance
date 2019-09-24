--  for mysql
--  说明： 生成随机字典数据。
--      入参是所有字典数据，使用英文逗号分开，不支持字典数据包含英文逗号。
--      返回其中一个字典数据，varchar格式。
-- 使用例子： select rand_dict('999001, 999002, 999003, 999004, 999005'); -- 输出 999003

delimiter $$
CREATE FUNCTION rand_dict(src VARCHAR(2048)) RETURNS VARCHAR(128) CHARSET utf8
BEGIN
    -- 计算字典的个数
    SET @tmp_dict_size = char_length(src) - char_length(replace(src, ',', '')) + 1;
    -- 生成随机返回的字典位置，从1开始
    SET @tmp_dict_index = ceil(rand() * (@tmp_dict_size + 1));
    -- 返回第 @tmp_dict_index 个元素前面的字符串
    SET @return_str = substring_index(src, ',', @tmp_dict_index);
    -- 返回第 @tmp_dict_index 元素
    SET @return_str = substring_index(@return_str, ',', -1);
    -- 去除左右空格
    SET @return_str = trim(@return_str);
    return @return_str;
END $$