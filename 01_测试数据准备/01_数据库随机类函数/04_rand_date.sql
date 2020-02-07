-- for mysql
-- 说明： 生成指定时间范围的随机时间
-- 使用例子： select rand_date(STR_TO_DATE('2018-05-21 23:59:59', '%Y-%m-%d %H:%i:%s'), now());  -- 输出 2019-07-13 16:22:44

delimiter $$
create function rand_date(start_time TIMESTAMP, end_time TIMESTAMP) RETURNS TIMESTAMP  NO SQL
BEGIN
    DECLARE tmp_year INT(4) DEFAULT 1970;
    DECLARE tmp_month VARCHAR(2) DEFAULT '01';
    DECLARE tmp_day VARCHAR(2) DEFAULT '01';
    DECLARE tmp_hour VARCHAR(2) DEFAULT '00';
    DECLARE tmp_minute VARCHAR(2) DEFAULT '00';
    DECLARE tmp_second VARCHAR(2) DEFAULT '00';
    DECLARE tmp_date_str VARCHAR(14);
    DECLARE tmp_min_val INT(4);
    DECLARE tmp_max_val INT(4);
    DECLARE tmp_rand_val INT(2);
    -- 是否在最大范围内
    DECLARE tmp_in_max_range INT(1) DEFAULT 0;
    DECLARE return_date TIMESTAMP;

    -- 生成随机年份
    SET tmp_min_val = DATE_FORMAT(start_time, '%Y');
    SET tmp_max_val = DATE_FORMAT(end_time, '%Y');
    SET tmp_year = tmp_min_val + CEIL(rand() * (tmp_max_val - tmp_min_val));
    IF tmp_year = tmp_max_val THEN
        -- 计算是否达到最大年份
        SET tmp_in_max_range = 1;
    END IF;

    -- 生成随机月份
    IF tmp_in_max_range = 1 THEN
        -- 如果达到最大年，则最大月份数取最大时间的月份
        SET tmp_min_val = DATE_FORMAT(start_time, '%m') + 0;
        SET tmp_max_val = DATE_FORMAT(end_time, '%m') + 0;
    ELSE
        SET tmp_min_val = 1;
        SET tmp_max_val = 12;
        SET tmp_in_max_range = 0;
    END IF;
    SET tmp_rand_val = tmp_min_val + CEIL(rand() * (tmp_max_val - tmp_min_val));
    IF tmp_rand_val < 10 THEN
        SET tmp_month = concat('0', tmp_rand_val);
    ELSE
        SET tmp_month = concat('', tmp_rand_val);
    END IF;
    IF tmp_in_max_range = 1 and tmp_month = tmp_max_val THEN
        -- 计算是否达到最大年月
        SET tmp_in_max_range = 1;
    ELSE
        SET tmp_in_max_range = 0;
    END IF;
		
    -- 生成随机日期
    IF tmp_in_max_range = 1 THEN
        -- 如果达到最大年月，则最大日期数取最大时间的日期
        SET tmp_min_val = DATE_FORMAT(start_time, '%d') + 0;
        SET tmp_max_val = DATE_FORMAT(end_time, '%d') + 0;
    ELSE
        SET tmp_min_val = 1;
        IF tmp_rand_val = 2 THEN
            -- 由于闰年需要进行判断，为避免代码过于复杂，便于理解，这里暂不考虑闰年有2月29日的情况
            SET tmp_max_val = 28;
        ELSEIF tmp_rand_val = 1 or tmp_rand_val = 3 or tmp_rand_val = 5 or tmp_rand_val = 7 or tmp_rand_val = 8 or tmp_rand_val = 10 or tmp_rand_val = 12 THEN
            SET tmp_max_val = 31;
        ELSE
            SET tmp_max_val = 30;
        END IF;
    END IF;
    SET tmp_rand_val = tmp_min_val + CEIL(rand() * (tmp_max_val - tmp_min_val));
    IF tmp_rand_val < 10 THEN
        SET tmp_day = concat('0', tmp_rand_val);
    ELSE
        SET tmp_day = concat('', tmp_rand_val);
    END IF;
    IF tmp_in_max_range = 1 and tmp_day = tmp_max_val THEN
        -- 计算是否达到最大年月日
        SET tmp_in_max_range = 1;
    ELSE
        SET tmp_in_max_range = 0;
    END IF;
		
    -- 生成随机小时
    IF tmp_in_max_range = 1 THEN
        -- 如果达到最大年月日，则最大小时数取最大时间的小时
        SET tmp_min_val = DATE_FORMAT(start_time, '%H') + 0;
        SET tmp_max_val = DATE_FORMAT(end_time, '%H') + 0;
    ELSE
        SET tmp_min_val = 0;
        SET tmp_max_val = 23;
    END IF;
    SET tmp_rand_val = tmp_min_val + CEIL(rand() * (tmp_max_val - tmp_min_val));
    IF tmp_rand_val < 10 THEN
        SET tmp_hour = concat('0', tmp_rand_val);
    ELSE
        SET tmp_hour = concat('', tmp_rand_val);
    END IF;
    IF tmp_in_max_range = 1 and tmp_hour = tmp_max_val THEN
        -- 计算是否达到最大年月日时
        SET tmp_in_max_range = 1;
    ELSE
        SET tmp_in_max_range = 0;
    END IF;

-- 生成随机分钟
    IF tmp_in_max_range = 1 THEN
        -- 如果达到最大年月日时，则最大分钟数取最大时间的分钟
        SET tmp_min_val = DATE_FORMAT(start_time, '%i') + 0;
        SET tmp_max_val = DATE_FORMAT(end_time, '%i') + 0;
    ELSE
        SET tmp_min_val = 0;
        SET tmp_max_val = 59;
    END IF;
    SET tmp_rand_val = tmp_min_val + CEIL(rand() * (tmp_max_val - tmp_min_val));
    IF tmp_rand_val < 10 THEN
        SET tmp_minute = concat('0', tmp_rand_val);
    ELSE
        SET tmp_minute = concat('', tmp_rand_val);
    END IF;
    IF tmp_in_max_range = 1 and tmp_minute = tmp_max_val THEN
        -- 计算是否达到最大年月日时分
        SET tmp_in_max_range = 1;
    ELSE
        SET tmp_in_max_range = 0;
    END IF;

    -- 生成随机秒钟
    IF tmp_in_max_range = 1 THEN
        -- 如果达到最大年月日时分，则最大秒数取最大时间的秒
        SET tmp_min_val = DATE_FORMAT(start_time, '%s') + 0;
        SET tmp_max_val = DATE_FORMAT(end_time, '%s') + 0;
    ELSE
        SET tmp_min_val = 0;
        SET tmp_max_val = 59;
    END IF;
    SET tmp_rand_val = tmp_min_val + CEIL(rand() * (tmp_max_val - tmp_min_val));
    IF tmp_rand_val < 10 THEN
        SET tmp_second = concat('0', tmp_rand_val);
    ELSE
        SET tmp_second = concat('', tmp_rand_val);
    END IF;

    SET tmp_date_str = concat(tmp_year, tmp_month, tmp_day, tmp_hour, tmp_minute, tmp_second);
    SET return_date = str_to_date(tmp_date_str, '%Y%m%d%H%i%s');
    RETURN return_date;
END $$