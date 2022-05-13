package com.bob.test.core.attribute;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class CaseAttributes implements Attributes {

    private final Map<String, Object> attributes = new HashMap<>();

    private EnviromentAttributes enviromentAttributes;

    public CaseAttributes(EnviromentAttributes enviromentAttributes) {
        this.enviromentAttributes = enviromentAttributes;
    }

    public CaseAttributes() {
        this.enviromentAttributes = new EnviromentAttributes() {
            @Override
            public Object get(String key) {
                return null;
            }

            @Override
            public <T> T get(String key, Class<T> type) {
                return null;
            }

            @Override
            public <T> T get(String key, Class<T> type, T defaultValue) {
                return defaultValue;
            }
        };
    }



    @Override
    public Object get(String key) {
        if (attributes.containsKey(key)) {
            return attributes.get(key);
        } else {
            return enviromentAttributes.get(key);
        }
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        if (attributes.containsKey(key)) {
            return (T) attributes.get(key);
        } else {
            return enviromentAttributes.get(key, type);
        }
    }

    @Override
    public <T> T get(String key, Class<T> type, T defaultValue) {
        if (attributes.containsKey(key)) {
            return (T) attributes.getOrDefault(key, defaultValue);
        } else {
            return enviromentAttributes.get(key, type, defaultValue);
        }
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
