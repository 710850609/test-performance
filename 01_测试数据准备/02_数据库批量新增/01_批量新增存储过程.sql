-- for mysql
-- 通过生成动态SQL
-- account表结构参考 01_批量新增存储过程.sql里面的备注
-- SQL模板： insert into [table_name] ([columns]) ([values1])([values2])([values3])
-- 经过测试，动态sql里面的生成随机值的函数直接放在SQL里面，批次执行效率高,差距大概2s内。批次执行在2000-3000范围内效率最高，这个值可能会受实际SQL长度影响。

delimiter $$
CREATE PROCEDURE proc_insert_account_batch(in batch_num int(12))
BEGIN
	DECLARE tmp_sql LONGTEXT;
    SET @tmp_batch = 3000;
    SET @tmp_base_sql = 'insert into account(`id`, `name`, pwd, salt, gender, mobile, `state`, email, create_time, update_time, `uid`, last_login_time,last_login_ip,login_count) values ';
    SET @tmp_sql = @tmp_base_sql;
    SET @tmp_index = 0;
    SET autocommit = 1;
    SET @i = 0;
    while @i < batch_num do
        SET @tmp_sql = concat(@tmp_sql, '(NULL,\'', rand_chinese_name(), '\',\'', rand_str(8), '\',\'', rand_str(6), '\',', rand_dict('0,1,2'), ',\'', rand_mobile(), '\',\'', rand_dict('0,1'),'\',\'', concat(rand_str(6), '@qq.com'), '\',\'', rand_date('2010-01-01 00:00:00', now()), '\',NULL,replace(uuid(), \'-\', \'\'),NULL,\'', rand_ip(), '\',', rand_range_num(0,1000), '),');
        SET @i = @i + 1;
        IF mod(@i, @tmp_batch) = 0 THEN
            SET @tmp_sql = LEFT(@tmp_sql, CHAR_LENGTH(@tmp_sql) - 1);
            prepare stmt from @tmp_sql;
            execute stmt;
            SET @tmp_sql = @tmp_base_sql;
        END IF;
    END while;
    IF length(@tmp_sql) > length(@tmp_base_sql) THEN
        SET @tmp_sql = LEFT(@tmp_sql, CHAR_LENGTH(@tmp_sql) - 1);
        prepare stmt from @tmp_sql;
        execute stmt;
    END IF;
END $$