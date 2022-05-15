package com.bob.test.core.attribute;

import java.util.HashMap;
import java.util.Map;

public class CaseAttributes implements Attributes {

    private final Map<String, Object> attributes = new HashMap<>();

    @Override
    public Object get(String key) {
        return attributes.get(key);
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        return (T) attributes.get(key);
    }

    @Override
    public <T> T get(String key, Class<T> type, T defaultValue) {
        return (T) attributes.getOrDefault(key, defaultValue);
    }

    @Override
    public <T> void set(String key, T value) {
        if (key != null) {
            attributes.put(key, value);
        }
    }

    @Override
    public void remove(String key) {
        if (key != null && attributes.containsKey(key)) {
            attributes.remove(key);
        }
    }
}
