package com.bob.test.core.log;

public interface IReportLogger {

    default void info(String message) {}
    default void info(String messagePattern, Object... argArray) {}
    default void warn(String message) {}
    default void warn(String messagePattern, Object... argArray) {}

}
