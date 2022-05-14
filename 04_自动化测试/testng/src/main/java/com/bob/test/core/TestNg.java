package com.bob.test.core;

import com.bob.test.core.attribute.Attributes;
import com.bob.test.core.attribute.CaseAttributes;
import com.bob.test.core.attribute.EnviromentAttributes;
import com.bob.test.core.attribute.SpringBootEnviromentAttributes;
import com.bob.test.util.SpringUtil;
import org.springframework.stereotype.Component;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TestNg工具使用入口。
 * 如果是多线程模式运行，需要将此类配置成testng的listener再使用
 */
@Component
public class TestNg implements ITestListener {

    private static final ThreadLocal<Attributes> ATTRIBUTES_THREAD_LOCAL = new ThreadLocal<>();
    private static EnviromentAttributes enviromentAttributes = new SpringBootEnviromentAttributes();

    private static AtomicInteger fetchedVariablesInNonListenerModeCount = new AtomicInteger(0);

    public static Attributes attributes() {
        Attributes attributes = ATTRIBUTES_THREAD_LOCAL.get();
        if (attributes == null) {
            System.out.println("获取变量： " + Thread.currentThread().hashCode() + ":" + Thread.currentThread().getId() + " -> " + Thread.currentThread().getName());
            if (fetchedVariablesInNonListenerModeCount.addAndGet(1) > 1) {
                throw new RuntimeException("多线程运行模式，需要将TestNg配置成Listener");
            }
            ATTRIBUTES_THREAD_LOCAL.set(new CaseAttributes(enviromentAttributes));
        }
        return ATTRIBUTES_THREAD_LOCAL.get();
    }

//    @Override
//    public void onTestStart(ITestResult result) {
//        System.out.println("onTestStart:" + Thread.currentThread().getName());
//        ATTRIBUTES_THREAD_LOCAL.set(new CaseAttributes(enviromentAttributes));
//    }
//
//    @Override
//    public void onTestSuccess(ITestResult result) {
//        ATTRIBUTES_THREAD_LOCAL.remove();
//    }
//
//    @Override
//    public void onTestFailure(ITestResult result) {
//        ATTRIBUTES_THREAD_LOCAL.remove();
//    }
//
//    @Override
//    public void onTestSkipped(ITestResult result) {
//        ATTRIBUTES_THREAD_LOCAL.remove();
//    }
//
//    @Override
//    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
//        ATTRIBUTES_THREAD_LOCAL.remove();
//    }
//
//    @Override
//    public void onTestFailedWithTimeout(ITestResult result) {
//        ATTRIBUTES_THREAD_LOCAL.remove();
//    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("onStart:" + Thread.currentThread().getName());
        ATTRIBUTES_THREAD_LOCAL.set(new CaseAttributes(enviromentAttributes));
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("onFinish:" + Thread.currentThread().getName());
        ATTRIBUTES_THREAD_LOCAL.remove();
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("onTestStart:" + Thread.currentThread().hashCode() + " : " + Thread.currentThread().getName() + " -> " + Thread.currentThread().getId());
    }
}
