//package com.bob.test.core.report;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.markuputils.MarkupHelper;
//import com.aventstack.extentreports.model.Category;
//import com.aventstack.extentreports.model.NamedAttribute;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
//import com.aventstack.extentreports.reporter.configuration.Theme;
//import org.testng.*;
//import org.testng.xml.XmlSuite;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class ExtentTestNgReporterListener implements IReporter {
//    //生成的路径以及文件名
//    private static final String OUTPUT_FOLDER = "report-output/";
//
//    private ExtentReports extent;
//
//    @Override
//    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
//        init();
//        boolean createSuiteNode = false;
//        if (suites.size() > 1) {
//            createSuiteNode = true;
//        }
//        for (ISuite suite : suites) {
//            Map<String, ISuiteResult> result = suite.getResults();
//            //如果suite里面没有任何用例，直接跳过，不在报告里生成
//            if (result.size() == 0) {
//                continue;
//            }
//            //统计suite下的成功、失败、跳过的总用例数
//            int suiteFailSize = 0;
//            int suitePassSize = 0;
//            int suiteSkipSize = 0;
//            ExtentTest suiteTest = null;
//            //存在多个suite的情况下，在报告中将同一个一个suite的测试结果归为一类，创建一级节点。
//            if (createSuiteNode) {
//                suiteTest = extent.createTest(suite.getName()).assignCategory(suite.getName());
//            }
//            boolean createSuiteResultNode = false;
//            if (result.size() > 1) {
//                createSuiteResultNode = true;
//            }
//            for (ISuiteResult r : result.values()) {
//                ExtentTest resultNode;
//                ITestContext context = r.getTestContext();
//                if (createSuiteResultNode) {
//                    //没有创建suite的情况下，将在SuiteResult的创建为一级节点，否则创建为suite的一个子节点。
//                    if (null == suiteTest) {
//                        resultNode = extent.createTest(r.getTestContext().getName());
//                    } else {
//                        resultNode = suiteTest.createNode(r.getTestContext().getName());
//                    }
//                } else {
//                    resultNode = suiteTest;
//                }
//                if (resultNode != null) {
//                    resultNode.getModel().setName(suite.getName() + " : " + r.getTestContext().getName());
//                    if (resultNode.getModel().hasCategory()) {
//                        resultNode.assignCategory(r.getTestContext().getName());
//                    } else {
//                        resultNode.assignCategory(suite.getName(), r.getTestContext().getName());
//                    }
//                    resultNode.getModel().setStartTime(r.getTestContext().getStartDate());
//                    resultNode.getModel().setEndTime(r.getTestContext().getEndDate());
//                    //统计SuiteResult下的数据
//                    int passSize = r.getTestContext().getPassedTests().size();
//                    int failSize = r.getTestContext().getFailedTests().size();
//                    int skipSize = r.getTestContext().getSkippedTests().size();
//                    suitePassSize += passSize;
//                    suiteFailSize += failSize;
//                    suiteSkipSize += skipSize;
//                    if (failSize > 0) {
//                        resultNode.getModel().setStatus(Status.FAIL);
//                    }
//                    resultNode.getModel().setDescription(String.format("通过: %s ; 失败: %s ; 跳过: %s ;", passSize, failSize, skipSize));
//                }
//                buildTestNodes(resultNode, context.getFailedTests(), Status.FAIL);
//                buildTestNodes(resultNode, context.getSkippedTests(), Status.SKIP);
//                buildTestNodes(resultNode, context.getPassedTests(), Status.PASS);
//            }
//            if (suiteTest != null) {
//                suiteTest.getModel().setDescription(String.format("通过: %s ; 失败: %s ; 跳过: %s ;", suitePassSize, suiteFailSize, suiteSkipSize));
//                if (suiteFailSize > 0) {
//                    suiteTest.getModel().setStatus(Status.FAIL);
//                }
//            }
//        }
//        extent.flush();
//    }
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
//    private void buildTestNodes(ExtentTest extenttest, IResultMap tests, Status status) {
//        //存在父节点时，获取父节点的标签
//        String[] categories = new String[0];
//        if (extenttest != null) {
//            Set<Category> categorySet = extenttest.getModel().getCategorySet();
//            categories = categorySet.stream().map(NamedAttribute::getName).toArray(String[]::new);
//        }
//
//        ExtentTest test;
//
//        if (tests.size() > 0) {
//            //调整用例排序，按时间排序
//            Set<ITestResult> treeSet = new TreeSet<ITestResult>(new Comparator<ITestResult>() {
//                @Override
//                public int compare(ITestResult o1, ITestResult o2) {
//                    return o1.getStartMillis() < o2.getStartMillis() ? -1 : 1;
//                }
//            });
//            treeSet.addAll(tests.getAllResults());
//            for (ITestResult result : treeSet) {
//                Object[] parameters = result.getParameters();
//                String name = result.getMethod().getDescription();
//                //如果有参数，则使用参数的toString组合代替报告中的name
//                for (Object param : parameters) {
//                    name += param.toString();
//                }
//                if (name.length() > 0) {
//                    if (name.length() > 50) {
//                        name = name.substring(0, 49) + "...";
//                    }
//                } else {
//                    name = result.getMethod().getMethodName();
//                }
//                if (extenttest == null) {
//                    test = extent.createTest(name);
//                } else {
//                    //作为子节点进行创建时，设置同父节点的标签一致，便于报告检索。
//                    test = extenttest.createNode(name).assignCategory(categories);
//                }
//                // 打标签
//                for (String group : result.getMethod().getGroups()) {
//                    test.assignCategory(group);
//                }
//                // 输出日志打印
//                List<String> outputList = Reporter.getOutput(result);
//                for (String output : outputList) {
//                    //将用例的log输出报告中
//                    test.log(Status.INFO, output);
//                }
//                if (result.getThrowable() != null) {
//                    test.log(status, result.getThrowable());
//                } else {
//                    test.log(status, "测试结果： " + status.getName());
//                }
//                test.getModel().setStartTime(new Date(result.getStartMillis()));
//                test.getModel().setEndTime(new Date(result.getEndMillis()));
//            }
//        }
//    }
//
//    private static final String CODE1 = "{\n    \"theme\": \"standard\",\n    \"encoding\": \"utf-8\n}";
//    private static final String CODE2 = "{\n    \"protocol\": \"HTTPS\",\n    \"timelineEnabled\": false\n}";
//
//    public static void main(String[] args) throws ClassNotFoundException {
//        ExtentReports extent = new ExtentReports();
//        ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark/Spark.html");
//        spark.config(ExtentSparkReporterConfig.builder()
////                        .resourceCDN("extentreports.com")
//                .resourceCDN("cdn.staticaly.com")
//                .offlineMode(true)
//                .build());
//        extent.attachReporter(spark);
//
//        extent.createTest("ScreenCapture")
//                .addScreenCaptureFromPath("extent.png")
//                .pass(MediaEntityBuilder.createScreenCaptureFromPath("extent.png").build());
//
//        extent.createTest("LogLevels")
//                .info("info")
//                .pass("pass")
//                .warning("warn")
//                .skip("skip")
//                .fail("fail");
//
//        extent.createTest("CodeBlock").generateLog(
//                Status.PASS,
//                MarkupHelper.createCodeBlock(CODE1, CODE2));
//
//        extent.createTest("ParentWithChild")
//                .createNode("Child")
//                .pass("This test is created as a toggle as part of a child test of 'ParentWithChild'");
//
//        extent.createTest("Tags")
//                .assignCategory("MyTag")
//                .pass("The test 'Tags' was assigned by the tag <span class='badge badge-primary'>MyTag</span>");
//
//        extent.createTest("Authors")
//                .assignAuthor("TheAuthor")
//                .pass("This test 'Authors' was assigned by a special kind of author tag.");
//
//        extent.createTest("Devices")
//                .assignDevice("TheDevice")
//                .pass("This test 'Devices' was assigned by a special kind of devices tag.");
//
//        extent.createTest("林勃")
//        ;
//        extent.createTest("Exception! <i class='fa fa-frown-o'></i>")
//                .fail(new RuntimeException("A runtime exception occurred!"));
//
//        extent.flush();
//    }
//
//}
