package com.bob.test.core;

import com.bob.test.core.attribute.Attributes;
import com.bob.test.core.attribute.CaseAttributes;
import com.bob.test.core.attribute.EnviromentAttributes;
import com.bob.test.core.attribute.SpringBootEnviromentAttributes;
import com.bob.test.core.log.IReportLogger;
import com.bob.test.core.log.ReportLoggerFactory;

/**
 * Test工具使用入口。
 */
public class Tests {

    private static final ThreadLocal<Attributes> ATTRIBUTES_THREAD_LOCAL = new InheritableThreadLocal<>();

    private static final EnviromentAttributes ENV = new SpringBootEnviromentAttributes();

    public static EnviromentAttributes env() {
        return ENV;
    }

    public static Attributes attributes() {
        Attributes attributes = ATTRIBUTES_THREAD_LOCAL.get();
        if (attributes == null) {
            ATTRIBUTES_THREAD_LOCAL.set(new CaseAttributes());
        }
        return ATTRIBUTES_THREAD_LOCAL.get();
    }

    public static IReportLogger logger(Object target) {
        return ReportLoggerFactory.getLogger(target);
    }

}
