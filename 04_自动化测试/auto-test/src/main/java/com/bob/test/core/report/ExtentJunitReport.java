//package com.bob.test.core.report;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
//import com.aventstack.extentreports.reporter.configuration.Theme;
//import org.junit.jupiter.api.extension.*;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class ExtentJunitReport implements TestWatcher, BeforeAllCallback, AfterAllCallback,
//        BeforeTestExecutionCallback, AfterTestExecutionCallback {
//
//    //生成的路径以及文件名
//    private static final String OUTPUT_FOLDER = "report-output/";
//
//    private ExtentReports extent;
//
//    private Map<String, ExtentTest> extentTestMap = new ConcurrentHashMap<>();
//    private Map<String, ExtentTest> extentTestNodeMap = new ConcurrentHashMap<>();
//
//    private static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();
//
//    private void init() {
//        //文件夹不存在的话进行创建
//        File reportDir = new File(OUTPUT_FOLDER);
//        if (!reportDir.exists() && !reportDir.isDirectory()) {
//            reportDir.mkdir();
//        }
//        String fileName = new SimpleDateFormat("yyyy-MM-dd_HH-mm-dd").format(new Date());
//        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(OUTPUT_FOLDER + fileName + ".html");
//        sparkReporter.config(ExtentSparkReporterConfig.builder()
//                // 为支持访问不到国外资源，使用离线模式
//                .offlineMode(true)
//                .documentTitle("自动化测试报告")
//                .reportName("自动化测试报告")
//                .theme(Theme.STANDARD)
//                .timeStampFormat("yyyy-MM-dd HH:mm:ss")
//                .encoding("UTF-8")
//                .build());
//        extent = new ExtentReports();
//        extent.attachReporter(sparkReporter);
//        extent.setReportUsesManualConfiguration(true);
//    }
//
//    @Override
//    public void testDisabled(ExtensionContext context, Optional<String> reason) {
//        ExtentTest test = getExtentTestNode(context);
//        test.skip(reason.get())
//                .getModel().setEndTime(new Date());
//        extentTestThreadLocal.remove();
//    }
//
//    @Override
//    public void testSuccessful(ExtensionContext context) {
//        ExtentTest test = getExtentTestNode(context);
//        test.pass("测试通过")
//                .getModel().setEndTime(new Date());
//        extentTestThreadLocal.remove();
//    }
//
//    @Override
//    public void testAborted(ExtensionContext context, Throwable cause) {
//        ExtentTest test = getExtentTestNode(context);
//        test.fail(cause)
//                .getModel().setEndTime(new Date());
//        extentTestThreadLocal.remove();
//    }
//
//    @Override
//    public void testFailed(ExtensionContext context, Throwable cause) {
//        ExtentTest test = getExtentTestNode(context);
//        test.fail(cause)
//                .getModel().setEndTime(new Date());
//        extentTestThreadLocal.remove();
//    }
//
//
//    private ExtentTest getExtentTest(ExtensionContext context) {
//        String displayName = context.getParent().get().getDisplayName();
//        String testName = Optional.ofNullable(displayName)
//                .orElse(context.getTestMethod().get().getName());
//        String cacheKey = context.getParent().get().getClass().getName() + ":" + testName;
//        ExtentTest test = extentTestMap.get(cacheKey);
//        if (test == null) {
//            Set<String> tags = context.getTags();
//            test = extent.createTest(testName)
//                    .assignCategory(tags.stream().toArray(String[]::new));
//            extentTestMap.put(cacheKey, test);
//        }
//        return test;
//    }
//
//    private ExtentTest getExtentTestNode(ExtensionContext context) {
//        String cacheKey = context.getUniqueId();
//        ExtentTest testNode = extentTestNodeMap.get(cacheKey);
//        if (testNode == null) {
//            ExtentTest test = getExtentTest(context);
//            String displayName = context.getDisplayName();
//            String nodeName = Optional.ofNullable(displayName)
//                    .orElse(context.getTestMethod().get().getName());
//            testNode = test.createNode(nodeName);
//            extentTestNodeMap.put(cacheKey, testNode);
//        }
//        return testNode;
//    }
//
//    @Override
//    public void beforeAll(ExtensionContext extensionContext) throws Exception {
//        init();
//    }
//
//    @Override
//    public void afterAll(ExtensionContext context) throws Exception {
//        extent.flush();
//    }
//
//    @Override
//    public void beforeTestExecution(ExtensionContext context) throws Exception {
//        ExtentTest test = getExtentTestNode(context);
//        test.info("测试开始").getModel().setStartTime(new Date());
//        extentTestThreadLocal.set(test);
//    }
//
//    @Override
//    public void afterTestExecution(ExtensionContext context) throws Exception {
////        ExtentTest test = getExtentTestNode(context);
////        test.getModel().setEndTime(new Date());
////        extentTestThreadLocal.remove();
//    }
//
//    public static ExtentTest getExtentTest() {
//        return extentTestThreadLocal.get();
//    }
//}
