package com.bob.test.core.attribute;

public interface EnviromentAttributes {
    Object get(String key);
    <T> T get(String key, Class<T> type);
    <T> T get(String key, Class<T> type, T defaultValue);

}
