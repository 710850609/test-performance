package com.bob.test.cases;

import com.apifan.common.random.source.NumberSource;
import com.bob.test.core.TestNg;
import com.bob.test.service.PayService;
import lombok.CustomLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.collections.CollectionUtils;

import java.util.List;

/**
 * 支付用例
 */
@CustomLog
@SpringBootTest
public class Pay extends AbstractTestNGSpringContextTests {

    @Autowired
    private PayService payService;

    @Test(description = "统一下单", priority = 1)
    public void unifiedOrder() {
        String orderNo = "101-" + NumberSource.getInstance().randomInt(1000000, 9999999);
        String payOrderNo = payService.unifiedOrder(orderNo, null);
        log.info("电商订单号【{}}】统一下单，对应支付订单号【{}】", orderNo, payOrderNo);
        Assert.assertNotEquals(payOrderNo, null, "下支付单失败");
        TestNg.attributes().set("payOrderNo", payOrderNo);
    }

    @Test(description = "获取支付方式", priority = 2)
    public void initPay() {
        String payOrderNo = TestNg.attributes().get("payOrderNo", String.class);
        List<String> payWayList = payService.initPay(payOrderNo);
        log.info("【{}】支付订单可用支付方式：【{}】", payOrderNo, payWayList);
        TestNg.attributes().set("payWayList", payWayList);
    }

    @Test(description = "确认支付", priority = 3)
    public void pay() {
        String payOrderNo = TestNg.attributes().get("payOrderNo", String.class);
        List<String> payWayList = (List<String>) TestNg.attributes().get("payWayList");
        Assert.assertTrue(CollectionUtils.hasElements(payWayList), "没有可用支付方式");
        String payWay = payWayList.get(0);
        payService.pay(payOrderNo, payWay);
        log.info("【{}】订单使用【{}】方式支付", payOrderNo, payWay);
    }

    @Test(description = "查询支付结果", priority = 4, dependsOnMethods = {"pay"})
    public void query() {
        String payOrderNo = TestNg.attributes().get("payOrderNo", String.class);
        boolean result = payService.query(payOrderNo);
        log.info("【{}】订单支付结果：{}", payOrderNo, result);
    }
}
