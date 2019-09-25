package core.subassembly;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成随机日期
 */
public class RandomDate {

    /**
     * 生成随机日期
     * @param min   最小值 
     * @param max   最大值
     * @return
     */
    public static Date getRange(Date min, Date max) {
        if (min == null || max == null) {
            throw new RuntimeException("日期最小值、最大值不能为空");
        }
        long minTime = min.getTime();
        long maxTime = max.getTime();
        Double d = Math.random() * (maxTime - minTime);
        return new Date(minTime + d.longValue());
    }
    
    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date min = sdf.parse("2018-01-01 00:00:00");
        Date max = sdf.parse("2019-01-01 00:00:00");
        Date date = RandomDate.getRange(min, max);
        System.out.println(sdf.format(date));
    }
}