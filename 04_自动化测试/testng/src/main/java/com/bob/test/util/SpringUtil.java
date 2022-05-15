package com.bob.test.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Spring工具，用于手动获取spring管理的java对象，或环境变量
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringUtil.context = context;
    }

    public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return context.getBeansOfType(type);
    }

    public static <T> T getBean(String beanName,Class<T> clazz){
        return context.getBean(beanName, clazz);
    }

    public static String getEnvironmentProperty(String key){
        return context.getEnvironment().getProperty(key);
    }

    public static String getEnvironmentProperty(String key, String defaultValue){
        return context.getEnvironment().getProperty(key, defaultValue);
    }

    public static <T> T getEnvironmentProperty(String key, Class<T> targetType){
        return context.getEnvironment().getProperty(key, targetType);
    }

    public static <T> T getEnvironmentProperty(String key, Class<T> targetType, T defaultValue){
        return context.getEnvironment().getProperty(key, targetType, defaultValue);
    }

}
