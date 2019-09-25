package core.subassembly;

/**
 * 生成随机数字
 */
public class RandomNum {

    /**
     * 生成固定长度的随机整数
     */
    public static int getFixed(int len) {
        if (len > 19) {
            throw new RuntimeException("最大只能19位数");
        }
        double d = Math.random();
        for (int i = 0; i < len; i++) {
            d = d * 10;
        }
        return ((Double) Math.ceil(d)).intValue();
    }

    /**
     * 生成指定范围的随机正整数，包括入参中的最大、最小值
     * @param min   最小值
     * @param max   最大值
     * @return
     */
    public static int getRange(int min, int max) {
        Double d = Math.random() * (max - min);
        return d.intValue() + min;
    }

    public static void main(String[] args) {
        int i = RandomNum.getFixed(19);
        System.out.println(i);
        i = RandomNum.getRange(1000, 1000);
        System.out.println(i);
    }
}