package com.bob.test.core.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author BOB
 */
public class ExtentReport {

    //生成的路径以及文件名
    private static final String OUTPUT_FOLDER = "report-output/";

    private Map<String, ExtentTest> extentTestParentMap = new ConcurrentHashMap<>();
    private Map<String, ExtentTest> extentTestNodeMap = new ConcurrentHashMap<>();

    private static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();

    private ExtentReports extent;

    /**
     * 生成报告文件前缀
     * @param reportFilePrefix
     */
    public ExtentReport() {
        init(null);
    }

    public ExtentReport(String reportFilePrefix) {
        init(reportFilePrefix);
    }

    protected void init(String reportFilePrefix) {
        //文件夹不存在的话进行创建
        File reportDir = new File(OUTPUT_FOLDER);
        if (!reportDir.exists() && !reportDir.isDirectory()) {
            reportDir.mkdir();
        }
        String fileName = new SimpleDateFormat("yyyy-MM-dd_HH-mm-dd").format(new Date());
        String filePath = null;
        if (StringUtils.isBlank(reportFilePrefix)) {
            filePath = OUTPUT_FOLDER + fileName + ".html";
        } else {
            filePath = OUTPUT_FOLDER + reportFilePrefix + "-" + fileName + ".html";
        }
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(filePath);
        sparkReporter.config(ExtentSparkReporterConfig.builder()
                // 为支持访问不到国外资源，使用离线模式
                .offlineMode(true)
                .documentTitle("自动化测试报告")
                .reportName("自动化测试报告")
                .theme(Theme.STANDARD)
                .timeStampFormat("yyyy-MM-dd HH:mm:ss")
                .encoding("UTF-8")
                .build());
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setReportUsesManualConfiguration(true);
    }

    public static void info(String messagePattern, Object... argArray) {
        ExtentTest extentTest = extentTestThreadLocal.get();
        if (extentTest != null) {
            String message = formatMessage(messagePattern, argArray);
            extentTest.info(message);
        }
    }

    public static void warn(String messagePattern, Object... argArray) {
        ExtentTest extentTest = extentTestThreadLocal.get();
        if (extentTest != null) {
            String message = formatMessage(messagePattern, argArray);
            extentTest.warning(message);
        }
    }
    private static String formatMessage(String messagePattern, Object... argArray) {
        return MessageFormatter.arrayFormat(messagePattern, argArray).getMessage();
    }


    public void flush() {
        extent.flush();
    }

    public void addUnit(String unitParentName, String unitName, String... tags) {
        ExtentTest testNode = getExtentTestNode(unitParentName, unitName);
        testNode.assignCategory(tags);
        testNode.getModel().setStartTime(new Date());
        extentTestThreadLocal.set(testNode);
    }

    public void info(String unitParentName, String unitName, String message) {
        ExtentTest testNode = getExtentTestNode(unitParentName, unitName);
        testNode.info(message)
                .getModel().setEndTime(new Date());
    }

    public void warning(String unitParentName, String unitName, String message) {
        ExtentTest testNode = getExtentTestNode(unitParentName, unitName);
        testNode.warning(message)
                .getModel().setEndTime(new Date());
    }

    public void pass(String unitParentName, String unitName, String message) {
        ExtentTest testNode = getExtentTestNode(unitParentName, unitName);
        testNode.pass(message)
                .getModel().setEndTime(new Date());
    }

    public void fail(String unitParentName, String unitName, String message) {
        ExtentTest testNode = getExtentTestNode(unitParentName, unitName);
        testNode.fail(message)
                .getModel().setEndTime(new Date());
    }

    protected void fail(String unitParentName, String unitName, Throwable cause) {
        ExtentTest testNode = getExtentTestNode(unitParentName, unitName);
        testNode.fail(cause)
                .getModel().setEndTime(new Date());
    }

    public void skip(String unitParentName, String unitName, String message) {
        ExtentTest testNode = getExtentTestNode(unitParentName, unitName);
        testNode.skip(message)
                .getModel().setEndTime(new Date());
    }

    private ExtentTest getExtentTestNode(String unitParentName, String unitName) {
        String cacheKey = unitParentName + ":" + unitName;
        ExtentTest testNode = extentTestNodeMap.get(cacheKey);
        if (testNode == null) {
            ExtentTest test = getExtentTest(unitParentName);
            testNode = test.createNode(unitName);
            extentTestNodeMap.put(cacheKey, testNode);
        }
        return testNode;
    }
    private ExtentTest getExtentTest(String unitParentName) {
        ExtentTest test = extentTestParentMap.get(unitParentName);
        if (test == null) {
            test = extent.createTest(unitParentName);
            extentTestParentMap.put(unitParentName, test);
        }
        return test;
    }

}
