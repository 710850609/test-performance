package com.bob.test.core.log;

import com.bob.test.core.report.ExtentReport;
import org.slf4j.Logger;

/**
 * @author BOB
 */
public class ReportLoggerWithSl4j implements IReportLogger {

    private Logger logger;

    public ReportLoggerWithSl4j(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String messagePattern, Object... argArray) {
        if (logger != null) {
            logger.info(messagePattern, argArray);
        }
        ExtentReport.info(messagePattern, argArray);
    }

    @Override
    public void warn(String messagePattern, Object... argArray) {
        if (logger != null) {
            logger.warn(messagePattern, argArray);
        }
        ExtentReport.warn(messagePattern, argArray);
    }

    @Override
    public void info(String message) {
        this.info(message, null);
    }

    @Override
    public void warn(String message) {
        this.warn(message, null);
    }

}
