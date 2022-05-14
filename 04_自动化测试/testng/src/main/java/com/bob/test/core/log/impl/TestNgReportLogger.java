package com.bob.test.core.log.impl;

import com.bob.test.core.log.IReportLogger;
import org.testng.Reporter;

/**
 * testNg报告记录器
 */
public class TestNgReportLogger implements IReportLogger {

    @Override
    public void trace(String message) {
        Reporter.log(message);
    }

    @Override
    public void debug(String message) {
        Reporter.log(message);
    }

    @Override
    public void info(String message) {
        Reporter.log(message);
    }

    @Override
    public void warn(String message) {
        Reporter.log(message);
    }

    @Override
    public void error(String message) {
        Reporter.log(message);
    }
}
