package com.bob.test.util;

import com.bob.test.core.attribute.Attributes;
import com.bob.test.core.attribute.CaseAttributes;
import com.bob.test.core.attribute.EnviromentAttributes;
import org.testng.ITestContext;
import org.testng.ITestListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TestNg工具使用入口。
 * 如果是多线程模式运行，需要将此类配置成testng的listener再使用
 */
public class TestNg implements ITestListener {

    private static final ThreadLocal<Attributes> ATTRIBUTES_THREAD_LOCAL = new ThreadLocal<>();
    private static EnviromentAttributes enviromentAttributes = SpringUtil.getBean(EnviromentAttributes.class);

    private static AtomicInteger fetchedVariablesInNonListenerModeCount = new AtomicInteger(0);

    public static Attributes attributes() {
        Attributes attributes = ATTRIBUTES_THREAD_LOCAL.get();
        if (attributes == null) {
            if (fetchedVariablesInNonListenerModeCount.addAndGet(1) > 1) {
                throw new RuntimeException("多线程运行模式，需要将TestNg配置成Listener");
            }
            ATTRIBUTES_THREAD_LOCAL.set(new CaseAttributes(enviromentAttributes));
        }
        return ATTRIBUTES_THREAD_LOCAL.get();
    }

    @Override
    public void onStart(ITestContext context) {
        ATTRIBUTES_THREAD_LOCAL.set(new CaseAttributes(enviromentAttributes));
    }

    @Override
    public void onFinish(ITestContext context) {
        ATTRIBUTES_THREAD_LOCAL.remove();
    }
}
