package com.bob.test.core.attribute;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBootEnviromentAttributes implements EnviromentAttributes, ApplicationContextAware {

    @Autowired
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public Object get(String key) {
        return context.getEnvironment().getProperty(key);
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        return context.getEnvironment().getProperty(key, type);
    }

    @Override
    public <T> T get(String key, Class<T> type, T defaultValue) {
        return context.getEnvironment().getProperty(key, type, defaultValue);
    }
}
