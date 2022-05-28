package com.bob.test.core.report;

import org.apache.commons.lang3.StringUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author BOB
 */
public class TestNgExtentReport implements ITestListener {

    /**
     * 所有用例输出到一个报告
     */
    private static ExtentReport extentReport;

    @Override
    public void onTestStart(ITestResult result) {
        String unitParentName = getUnitParentName(result);
        String unitName = getUnitName(result);
        String[] groups = result.getMethod().getGroups();
        String name = result.getTestContext().getName();
        List<String> tagList = new ArrayList<>();
        tagList.add(name);
        if (groups != null) {
            Stream.of(groups).forEach(e -> tagList.add(e));
        }
        extentReport.addUnit(unitParentName, unitName, tagList.toArray(new String[]{}));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String unitParentName = getUnitParentName(result);
        String unitName = getUnitName(result);
        extentReport.pass(unitParentName, unitName, "测试通过");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String unitParentName = getUnitParentName(result);
        String unitName = getUnitName(result);
        Throwable throwable = result.getThrowable();
        extentReport.fail(unitParentName, unitName, throwable);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String unitParentName = getUnitParentName(result);
        String unitName = getUnitName(result);
        String causedBy = result.getSkipCausedBy()
                .stream()
                .map(e -> "【" + Optional.ofNullable(e.getDescription()).orElse(e.getMethodName()) + "】")
                .collect(Collectors.joining(","));
        extentReport.skip(unitParentName, unitName, "跳过执行，引起的用例：" + causedBy);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        int successPercentage = result.getMethod().getSuccessPercentage();
        ExtentReport.warn("测试不通过，通过率： {}%" + successPercentage);
        this.onTestFailure(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        long timeOut = result.getMethod().getTimeOut();
        if (timeOut > 0) {
            ExtentReport.warn("没有在指定{}毫秒内执行完", timeOut);
        }
        this.onTestFailure(result);
    }

    @Override
    public void onStart(ITestContext context) {
        if (extentReport == null) {
            extentReport = new ExtentReport();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReport.flush();
    }
    private String getUnitName(ITestResult result) {
        String unitName = result.getMethod().getDescription();
        if (StringUtils.isBlank(unitName)) {
            unitName = result.getMethod().getMethodName();
        }
        return unitName;
    }

    private String getUnitParentName(ITestResult result) {
        String parentName = result.getTestContext().getName();
        if (StringUtils.isBlank(parentName)) {
            parentName = result.getMethod().getInstance().getClass().getName();
        }
        return parentName;
    }

}
