package com.bob.test.core.log;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.testng.Reporter;
import org.testng.collections.Maps;
import org.testng.collections.Sets;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class TestNgLoggerFactory {

    private static Map<String, String> supportReportMethods = Maps.newHashMap();
    static {
        supportReportMethods.put("info", "isInfoEnabled");
        supportReportMethods.put("warn", "isWarnEnabled");
        supportReportMethods.put("error", "isErrorEnabled");
    }

    public static Logger getLogger(String name) {
        ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
        Logger logger = iLoggerFactory.getLogger(name);
        return proxyLogger(logger);
    }
    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    private static Logger proxyLogger(Logger logger) {
        return (Logger) Proxy.newProxyInstance(logger.getClass().getClassLoader(), logger.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        String checkMethodName = supportReportMethods.get(method.getName());
                        Method checkMethod = logger.getClass().getMethod(checkMethodName, null);
                        boolean isEnabled = (boolean) checkMethod.invoke(logger, null);
                        if (supportReportMethods.keySet().contains(method.getName()) && isEnabled) {
                            Object[] params = Arrays.stream(objects).skip(1).filter(e -> {
                                return !e.getClass().isAssignableFrom(Marker.class) || !e.getClass().isAssignableFrom(Throwable.class);
                            }).toArray();
                            String message = MessageFormatter.arrayFormat((String) objects[0], params).getMessage();
                            Reporter.log(message);
                        }
                        return method.invoke(logger, objects);
                    }
                });
    }

}
