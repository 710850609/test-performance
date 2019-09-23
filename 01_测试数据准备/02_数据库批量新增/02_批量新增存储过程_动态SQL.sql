-- for mysql
-- 通过生成动态SQL
-- account表结构参考 01_批量新增存储过程.sql里面的备注
-- SQL模板： insert into [table_name] ([columns]) ([values1])([values2])([values3])

delimiter $$
CREATE PROCEDURE proc_insert_account_batch(in batch_num int(12))
BEGIN
	DECLARE tmp_sql LONGTEXT;
    SET @tmp_batch = 1000;
    SET @tmp_base_sql = 'insert into account(`id`, `name`, pwd, salt, gender, mobile, email, create_time, update_time, `uid`, last_login_time) values ';
    SET @tmp_sql = @tmp_base_sql;
    SET @tmp_index = 0;
    SET autocommit = 1;
    SET @i = 0;
    while @i < batch_num do
        SET @tmp_name = rand_chinese_name();
        SET @tmp_pwd = rand_str(8);
        SET @tmp_salt = rand_str(6);
        SET @tmp_mobile = rand_mobile();
        SET @tmp_email = concat(rand_str(6), '@qq.com');
        SET @tmp_create_time = rand_date('2010-01-01 00:00:00', now());
        SET @tmp_sql = concat(@tmp_sql, '(NULL,\'', @tmp_name, '\',\'', @tmp_pwd, '\',\'', @tmp_salt, '\',NULL,\'', @tmp_mobile, '\',\'', @tmp_email, '\',\'', @tmp_create_time, '\',NULL,replace(uuid(), \'-\', \'\'),NULL)');
        IF @i / @tmp_batch = 0 THEN
            select @tmp_sql;
            prepare stmt from @tmp_sql;
            execute stmt;
            SET @tmp_sql = @tmp_base_sql;
        END IF;
        SET @i = @i + 1;
    END while;
    IF length(@tmp_sql) > 0 THEN
        select @tmp_sql;
        prepare stmt from @tmp_sql;
        execute stmt;
    END IF;
END $$