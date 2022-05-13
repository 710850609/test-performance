package com.bob.test.core.log;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.testng.Reporter;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestNgLoggerFactory {

    public static Logger getLogger(String name) {
        ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
        return iLoggerFactory.getLogger(name);
    }
    public static Logger getLogger(Class<?> clazz) {
        Logger logger = getLogger(clazz.getName());
        return proxyLogger(logger);
    }

    private static Logger proxyLogger(Logger logger) {
        return (Logger) Proxy.newProxyInstance(logger.getClass().getClassLoader(), logger.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        if ("info".equals(method.getName())) {
                            Object[] params = Arrays.stream(objects).skip(1).filter(e -> e.getClass().isAssignableFrom(String.class)).toArray();
                            String message = MessageFormatter.arrayFormat((String) objects[0], params).getMessage();

                            System.out.println("输出报告： " + message);
                            Reporter.log(message);
                        }
                        return null;
//                        return method.invoke(logger, objects);
                    }
                });
    }

}
