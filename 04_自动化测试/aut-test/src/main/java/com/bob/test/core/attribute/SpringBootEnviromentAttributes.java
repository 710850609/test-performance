package com.bob.test.core.attribute;

import com.bob.test.util.SpringUtil;


/**
 * 加入spring管理，避免测试框架比spring先加载，出现空指针
 */
public class SpringBootEnviromentAttributes implements EnviromentAttributes {

    @Override
    public Object get(String key) {
        return SpringUtil.getEnvironmentProperty(key);
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        return SpringUtil.getEnvironmentProperty(key, type);
    }

    @Override
    public <T> T get(String key, Class<T> type, T defaultValue) {
        return SpringUtil.getEnvironmentProperty(key, type, defaultValue);
    }
}
