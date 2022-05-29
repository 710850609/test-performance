package com.bob.test.cases.testng;

import com.bob.test.core.Tests;
import com.bob.test.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@SpringBootTest
@Test(description = "退款流程")
public class Refund extends AbstractTestNGSpringContextTests {

    @Autowired
    private RefundService refundService;

    @Test(description = "退款申请", priority = 1)
    public void apply() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {

        }
        String payOrderNo = Tests.attributes().get("payOrderNo", String.class);
        Assert.assertTrue(payOrderNo != null, "支付订单号为空");
        String refundNo = refundService.apply(payOrderNo);
        Tests.logger(this).info("【{}】申请退款，退款单：{}", payOrderNo, refundNo);
        Tests.attributes().set("refundNo", refundNo);
    }

    @Test(description = "退款审核", priority = 2)
    public void check() {
        String refundNo = Tests.attributes().get("refundNo", String.class);
        boolean result = refundService.check(refundNo);
        Tests.logger(this).info("【{}】单退款结果：{}", refundNo, result);
    }

}