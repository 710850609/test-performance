package com.bob.test.scene;

import com.bob.test.service.RefundService;
import com.bob.test.util.TestNg;
import lombok.CustomLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@CustomLog
@SpringBootTest
public class Refund extends AbstractTestNGSpringContextTests {

    @Autowired
    private RefundService refundService;

    @Test(description = "退款申请", priority = 1)
    public void apply() {
        String payOrderNo = TestNg.attributes().get("payOrderNo", String.class);
        Assert.assertTrue(payOrderNo != null, "支付订单号为空");
        String refundNo = refundService.apply(payOrderNo);
        log.info("【{}】申请退款，退款单：{}", payOrderNo, refundNo);
        TestNg.attributes().set("refundNo", refundNo);
    }

    @Test(description = "退款审核", priority = 2)
    public void check() {
        String refundNo = TestNg.attributes().get("refundNo", String.class);
        boolean result = refundService.check(refundNo);
        log.info("【{}】单退款结果：{}", refundNo, result);
    }

}
