package com.bob.test.core.log;

public interface IReportLogger {

    default void trace(String message) {}
    default void debug(String message) {}
    default void info(String message) {}
    default void warn(String message) {}
    default void error(String message) {}

}
