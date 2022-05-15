package com.bob.test.core;

import com.bob.test.core.attribute.Attributes;
import com.bob.test.core.attribute.CaseAttributes;
import com.bob.test.core.attribute.EnviromentAttributes;
import com.bob.test.core.attribute.SpringBootEnviromentAttributes;
import org.testng.ITestContext;
import org.testng.ITestListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TestNg工具使用入口。
 * 如果是多线程模式运行，需要将此类配置成testng的listener再使用
 */
public class TestNg implements ITestListener {

    private static final ThreadLocal<Attributes> ATTRIBUTES_THREAD_LOCAL = new InheritableThreadLocal<>();

    private static AtomicInteger fetchedVariablesInNonListenerModeCount = new AtomicInteger(0);

    private static final EnviromentAttributes ENV = new SpringBootEnviromentAttributes();

    public static EnviromentAttributes env() {
        return ENV;
    }

    public static Attributes attributes() {
        Attributes attributes = ATTRIBUTES_THREAD_LOCAL.get();
        if (attributes == null) {
            if (fetchedVariablesInNonListenerModeCount.addAndGet(1) > 1) {
                throw new RuntimeException("多线程运行模式，需要将TestNg配置成Listener");
            }
            ATTRIBUTES_THREAD_LOCAL.set(new CaseAttributes());
        }
        return ATTRIBUTES_THREAD_LOCAL.get();
    }

    @Override
    public void onStart(ITestContext context) {
        ATTRIBUTES_THREAD_LOCAL.set(new CaseAttributes());
    }

    @Override
    public void onFinish(ITestContext context) {
        ATTRIBUTES_THREAD_LOCAL.remove();
    }

}
