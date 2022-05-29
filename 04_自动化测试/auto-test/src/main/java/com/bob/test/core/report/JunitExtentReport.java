package com.bob.test.core.report;

import org.junit.jupiter.api.extension.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author BOB
 */
public class JunitExtentReport implements TestWatcher, BeforeAllCallback, AfterAllCallback,
        BeforeTestExecutionCallback {

    private static ExtentReport extentReport;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        if (extentReport == null) {
            extentReport = new ExtentReport();
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        extentReport.flush();
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        String unitParentName = getUnitParentName(context);
        String unitName = getUnitName(context);
        String displayName = context.getParent().get().getDisplayName();
        List<String> tagList = new ArrayList<>();
        tagList.add(displayName);
        tagList.addAll(context.getTags());
        extentReport.addUnit(unitParentName, unitName, tagList.toArray(new String[]{}));
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        String unitParentName = getUnitParentName(context);
        String unitName = getUnitName(context);
        extentReport.skip(unitParentName, unitName, reason.get());
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        String unitParentName = getUnitParentName(context);
        String unitName = getUnitName(context);
        extentReport.pass(unitParentName, unitName, null);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        String unitParentName = getUnitParentName(context);
        String unitName = getUnitName(context);
        extentReport.fail(unitParentName, unitName, cause);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String unitParentName = getUnitParentName(context);
        String unitName = getUnitName(context);
        extentReport.fail(unitParentName, unitName, cause);
    }

    private String getUnitName(ExtensionContext context) {
        String displayName = context.getDisplayName();
        return Optional.ofNullable(displayName)
                .orElse(context.getTestMethod().get().getName());
    }

    private String getUnitParentName(ExtensionContext context) {
        String displayName = context.getParent().get().getDisplayName();
        return Optional.ofNullable(displayName)
                .orElse(context.getTestMethod().get().getClass().getName());
    }

}
