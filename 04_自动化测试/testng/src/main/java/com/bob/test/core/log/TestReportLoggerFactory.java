package com.bob.test.core.log;

import com.bob.test.core.log.impl.TestNgReportLogger;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TestReportLoggerFactory {


    private static ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
    private static IReportLogger reportLogger;

    static {
        reportLogger = new TestNgReportLogger();
    }

    public static Logger getLogger(String name) {
        Logger logger = loggerFactory.getLogger(name);
        return proxyLogger(logger);
    }
    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    private static Logger proxyLogger(Logger logger) {
        return (Logger) Proxy.newProxyInstance(logger.getClass().getClassLoader(), logger.getClass().getInterfaces(),
                new TestReportLoggerAdapter(logger, reportLogger));
    }

    static class TestReportLoggerAdapter implements InvocationHandler {

        private Logger logger;
        private IReportLogger reportLogger;
        public TestReportLoggerAdapter(Logger logger, IReportLogger reportLogger) {
            this.logger = logger;
            this.reportLogger = reportLogger;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            if (reportLogger == null) {
                return method.invoke(logger, objects);
            }
            String methodName = method.getName();
            switch (methodName) {
                case "trace":
                    return handleReport("isTraceEnabled", msg -> reportLogger.trace(msg), method, objects);
                case "debug":
                    return handleReport("isInfoEnabled", msg -> reportLogger.debug(msg), method, objects);
                case "info":
                    return handleReport("isInfoEnabled", msg -> reportLogger.info(msg), method, objects);
                case "warn":
                    return handleReport("isWarnEnabled", msg -> reportLogger.warn(msg), method, objects);
                case "error":
                    return handleReport("isErrorEnabled", msg -> reportLogger.error(msg), method, objects);
                default:
                    return method.invoke(logger, objects);
            }
        }


        private Object handleReport(String checkMethodName, Consumer<String> consumer, Method method, Object[] objects) throws Exception {
            Method checkMethod = logger.getClass().getMethod(checkMethodName, null);
            boolean isEnabled = (boolean) checkMethod.invoke(logger, null);
            Object result = method.invoke(logger, objects);
            if (isEnabled) {
                String message = formatMessage(objects);
                consumer.accept(message);
            }
            return result;
        }

        private String formatMessage(Object[] objects) {
            Object[] params = Arrays.stream(objects).skip(1).filter(e -> {
                return e == null || !e.getClass().isAssignableFrom(Marker.class) || !e.getClass().isAssignableFrom(Throwable.class);
            }).toArray();
            return MessageFormatter.arrayFormat((String) objects[0], params).getMessage();
        }
    }
}
