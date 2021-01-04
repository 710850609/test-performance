package org.bob.testing.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RunContext {

    private static Map<String, Object> contextValues = new ConcurrentHashMap<>();

    /**
     * 获取全局变量
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getGlobalValue(String key, Class<T> type) {
        return ((T) contextValues.get(key));
    }

    /**
     * 设置全局变量
     * @param key
     * @param value
     */
    public static void setGlobalValue(String key, Object value) {
        if (value != null) {
            contextValues.put(key, value);
        }
    }
}
