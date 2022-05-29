package com.bob.test.core.attribute;


/**
 * 变量存储
 */
public interface Attributes {

    /**
     * 获取指定key的变量
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 获取指定key的变量，并按指定类型返回
     * @param key
     * @param type
     * @return
     * @param <T>
     */
    <T> T get(String key, Class<T> type);

    /**
     * 获取指定key的变量，并按指定类型返回，当值为空时，返回默认值
     * @param key
     * @param type
     * @param defaultValue 默认值
     * @return
     * @param <T>
     */
    <T> T get(String key, Class<T> type, T defaultValue);

    /**
     * 设置变量
     * @param key
     * @param value
     * @param <T>
     */
    <T> void set(String key, T value);

    /**
     * 移除变量
     * @param key
     */
    void remove(String key);
}
