package com.bob.test.core.attribute;

import java.util.List;

public interface Attributes {

    Object get(String key);
    <T> T get(String key, Class<T> type);
    <T> T get(String key, Class<T> type, T defaultValue);

    <T> void set(String key, T value);

    void remove(String key);
}
