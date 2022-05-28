package com.bob.test.cases;

import com.bob.test.core.Tests;
import com.bob.test.core.report.JunitExtentReport;
import com.bob.test.service.RefundService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author BOB
 */
@SpringBootTest
@DisplayName("退款流程")
@ExtendWith(JunitExtentReport.class)
public class RefundJunit extends AbstractTestNGSpringContextTests {

    @Autowired
    private RefundService refundService;

    @BeforeAll
    public static void doBefore() {
        Tests.attributes().set("payOrderNo", "101-" + System.currentTimeMillis());
    }

    @Test
    @DisplayName("退款申请")
    @Tag("退款")
    @Timeout(value = 30, unit = SECONDS)
    public void apply() {
        String payOrderNo = Tests.attributes().get("payOrderNo", String.class);
        Assertions.assertTrue(payOrderNo != null, "支付订单号为空");
        String refundNo = refundService.apply(payOrderNo);
        Tests.logger(this).info("【{}】申请退款，退款单：{}", payOrderNo, refundNo);
        Tests.attributes().set("refundNo", refundNo);
    }

    @Test
    @DisplayName("退款审核")
    @Timeout(value = 30, unit = SECONDS)
    public void check() {
        String refundNo = Tests.attributes().get("refundNo", String.class);
        boolean result = refundService.check(refundNo);
        Tests.logger(this).info("【{}】单退款结果：{}", refundNo, result);
    }

}
