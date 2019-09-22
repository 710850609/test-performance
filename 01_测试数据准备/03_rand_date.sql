-- for mysql
-- 生成随机日期，从1970-01-01 00:00:00到

delimiter $$
create function rand_date() RETURNS TIMESTAMP
BEGIN
    DECLARE tmp_year int DEFAULT 1970;
    DECLARE tmp_month VARCHAR(2) DEFAULT '01';
    DECLARE tmp_day VARCHAR(2) DEFAULT '01';
    DECLARE tmp_hour VARCHAR(2) DEFAULT '00';
    DECLARE tmp_minute VARCHAR(2) DEFAULT '00';
    DECLARE tmp_second VARCHAR(2) DEFAULT '00';
    DECLARE tmp_date_str VARCHAR(14);
    DECLARE tmp_month_num INT(2);
    DECLARE tmp_max_day INT(2);
    DECLARE tmp_day_num INT(2);
    DECLARE tmp_hour_num int(2);
    DECLARE tmp_minute_num int(2);
    DECLARE tmp_second_num int(2);
    DECLARE return_date TIMESTAMP;

    -- 生成随机年份（从1970-当前年份）
    SET tmp_year = 1970 + CEIL(rand() * (date_format(now(), '%Y') - 1970 + 1));

    -- 生成随机月份(01-12)
		SET tmp_month_num = CEIL(rand() * 13);
    IF tmp_month_num < 10 THEN
        SET tmp_month = concat('0', tmp_month_num);
    ELSE
        SET tmp_month = concat('', tmp_month_num);
    END IF;

    -- 生成随机日期(这里简单实现，不支持闰月。取值：01-31))
    IF tmp_month_num = 2 THEN
        SET tmp_max_day = 28;
    ELSEIF tmp_month_num = 1 or tmp_month_num = 3 or tmp_month_num = 5 or tmp_month_num = 7 or tmp_month_num = 8 or tmp_month_num = 10 or tmp_month_num = 12 THEN
        SET tmp_max_day = 31;
    ELSE
        SET tmp_max_day = 30;
    END IF;
		SET tmp_day_num = CEIL(rand() * (tmp_max_day + 1));
    IF tmp_day_num < 10 THEN
        SET tmp_day = concat('0', tmp_day_num);
    ELSE
        SET tmp_day = concat('', tmp_day_num);
    END IF;

    -- 生成随机小时(00-23)
    SET tmp_hour_num = CEIL(rand() * 24);
    IF tmp_hour_num < 10 THEN
        SET tmp_hour = concat('0', tmp_hour_num);
    ELSE
        SET tmp_hour = concat('', tmp_hour_num);
    END IF;

    -- 生成随机分钟(00-59)
    SET tmp_minute_num = CEIL(rand() * 60);
    IF tmp_minute_num < 10 THEN
        SET tmp_minute = concat('0', tmp_minute_num);
    ELSE
        SET tmp_minute = concat('', tmp_minute_num);
    END IF;

    -- 生成随机秒钟(00-59)
    SET tmp_second_num = CEIL(rand() * 60);
    IF tmp_second_num < 10 THEN
        SET tmp_second = concat('0', tmp_second_num);
    ELSE
        SET tmp_second = concat('', tmp_second_num);
    END IF;

    SET tmp_date_str = concat(tmp_year, tmp_month, tmp_day, tmp_hour, tmp_minute, tmp_second);
    SET return_date = str_to_date(tmp_date_str, '%Y%m%d%H%i%s');
    RETURN return_date;
END $$