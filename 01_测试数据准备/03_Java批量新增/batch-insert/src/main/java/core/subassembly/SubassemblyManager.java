package core.subassembly;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LinBo
 * @date 2019-09-26 8:55
 */
public class SubassemblyManager {

    private static Map<String, Object[]> metas = new HashMap<>();

    public static final String RANDOM_DATE_RANGE = "core.subassembly.RandomDate.getRange";
    public static final String RANDOM_NUM_FIXED = "core.subassembly.RandomNum.getFixed";
    public static final String RANDOM_NUM_RANGE = "core.subassembly.RandomNum.getRange";
    public static final String RANDOM_STRING_FIXED = "core.subassembly.RandomString.getFixed";
    public static final String RANDOM_STRING_RANGE = "core.subassembly.RandomString.getRange";
    public static final String RANDOM_STRING_CHINESENAME = "core.subassembly.RandomString.getChineseName";
    public static final String RANDOM_STRING_MOBILE = "core.subassembly.RandomString.getMobile";
    public static final String RANDOM_STRING_ID_CARD_NO = "core.subassembly.RandomString.getIdCardNo";
    public static final String RANDOM_STRING_EMAIL = "core.subassembly.RandomString.getEmail";
    public static final String RANDOM_STRING_IP = "core.subassembly.RandomString.getIp";
    public static final String RANDOM_STRING_UUID = "core.subassembly.RandomString.getUuid";

    static {
        try {
            loadMethods(RANDOM_DATE_RANGE);
            loadMethods(RANDOM_NUM_FIXED);
            loadMethods(RANDOM_NUM_RANGE);
            loadMethods(RANDOM_STRING_FIXED);
            loadMethods(RANDOM_STRING_RANGE);
            loadMethods(RANDOM_STRING_CHINESENAME);
            loadMethods(RANDOM_STRING_MOBILE);
            loadMethods(RANDOM_STRING_ID_CARD_NO);
            loadMethods(RANDOM_STRING_EMAIL);
            loadMethods(RANDOM_STRING_IP);
            loadMethods(RANDOM_STRING_UUID);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void loadMethods(String methodStr) throws Exception {
        int index = methodStr.lastIndexOf(".");
        Class<?> clazz = Class.forName(methodStr.substring(0, index));
        Object obj = clazz.newInstance();
        Method[] allDeclaredMethods = ReflectionUtils.getAllDeclaredMethods(clazz);
        Method method = null;
        for (Method declaredMethod : allDeclaredMethods) {
            String name = declaredMethod.getName();
            if (name.equals(methodStr.substring(index + 1))) {
                method = declaredMethod;
                break;
            }
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] meta = new Object[parameterTypes.length + 2];
        meta[0] = obj;
        meta[1] = method;
        for (int i = 0; i < parameterTypes.length; i++) {
            meta[i + 2] = parameterTypes[i];
        }
        metas.put(methodStr, meta);
    }

    /**
     * 获取随机数据入口，不支持实现方法中含有数据、集合
     **/
    public static Object getData(String... params) throws Exception {
        String method = params[0];
        String[] methodParamStrs = Arrays.copyOfRange(params, 1, params.length);

        Object[] meta = metas.get(method);
        Object[] paramTypes = Arrays.copyOfRange(meta, 2, meta.length);

        if (paramTypes.length != methodParamStrs.length) {
            throw new RuntimeException("参数个数不一致");
        }
        Object[] methodParams = new Object[methodParamStrs.length];

        if (methodParams.length > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < paramTypes.length; i++) {
                Class clazz = (Class) paramTypes[i];
                String valueStr = methodParamStrs[i];
                Object value = null;
                if (clazz.isAssignableFrom(Integer.class) || "int".equals(clazz.getTypeName())) {
                    value = Integer.valueOf(valueStr);
                } else if (clazz.isAssignableFrom(Long.class) || "long".equals(clazz.getTypeName())) {
                    value = Long.valueOf(valueStr);
                } else if (clazz.isAssignableFrom(Double.class) || "double".equals(clazz.getTypeName())) {
                    value = Double.valueOf(valueStr);
                } else if (clazz.isAssignableFrom(Float.class) || "float".equals(clazz.getTypeName())) {
                    value = Float.valueOf(valueStr);
                } else if (clazz.isAssignableFrom(Boolean.class) || "boolean".equals(clazz.getTypeName())) {
                    value = Boolean.valueOf(valueStr);
                } else if (clazz.isAssignableFrom(Date.class)) {
                    value = sdf.parse(valueStr);
                } else {
                    value = methodParamStrs[i];
                }
                methodParams[i] = value;
            }
        }

        Object obj = ReflectionUtils.invokeMethod((Method) meta[1], meta[0], methodParams);
        return obj;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getData(RANDOM_NUM_FIXED, "5"));
        System.out.println(getData(RANDOM_NUM_RANGE, "999001", "999010"));
        System.out.println(getData(RANDOM_DATE_RANGE, "2018-01-01 00:00:00", "2019-01-01 00:00:00"));
        System.out.println(getData(RANDOM_STRING_FIXED, "6"));
        System.out.println(getData(RANDOM_STRING_RANGE, "a1b2c3", "3"));
        System.out.println(getData(RANDOM_STRING_CHINESENAME));
        System.out.println(getData(RANDOM_STRING_MOBILE));
        System.out.println(getData(RANDOM_STRING_ID_CARD_NO));
        System.out.println(getData(RANDOM_STRING_EMAIL));
        System.out.println(getData(RANDOM_STRING_IP));
        System.out.println(getData(RANDOM_STRING_UUID));
    }
}
