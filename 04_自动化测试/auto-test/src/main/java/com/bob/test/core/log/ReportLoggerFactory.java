package com.bob.test.core.log;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author BOB
 */
public class ReportLoggerFactory {

    private static ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
    private static final Map<String, IReportLogger> REPORT_LOGGER_MAP = new HashMap();
    private static String DEFAULT_LOGGER_NAME = ReportLoggerFactory.class.getName();

    public static IReportLogger getLogger(Object target) {
        String className = DEFAULT_LOGGER_NAME;
        if (target != null) {
            if (target instanceof Class) {
                className = ((Class) target).getName();
            } else {
                className = target.getClass().getName();
            }
        }
        IReportLogger iReportLogger = REPORT_LOGGER_MAP.get(className);
        if (iReportLogger == null) {
            Logger logger = loggerFactory.getLogger(className);
            iReportLogger = new ReportLoggerWithSl4j(logger);
            REPORT_LOGGER_MAP.put(className, iReportLogger);
        }
        return iReportLogger;
    }
}
