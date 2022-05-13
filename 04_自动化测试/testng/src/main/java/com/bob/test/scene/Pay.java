package com.bob.test.scene;

import com.bob.test.service.PayService;
import com.bob.test.util.TestNg;
import lombok.CustomLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.collections.CollectionUtils;

import java.util.List;

@CustomLog
@SpringBootTest
public class Pay extends AbstractTestNGSpringContextTests {

    @Autowired
    private PayService payService;

    @Test(priority = 1)
    public void unifiedOrder() {
        String orderNo = "101-" + System.currentTimeMillis();
        String payOrderNo = payService.unifiedOrder(orderNo, null);
        log.info("电商订单号【{}}】统一下单，对应支付订单号【{}】", orderNo, payOrderNo);
        Assert.assertNotEquals(payOrderNo, null, "下支付单失败");
        TestNg.attributes().set("payOrderNo", payOrderNo);
    }

    @Test(priority = 2)
    public void initPay() {
        String payOrderNo = TestNg.attributes().get("payOrderNo", String.class);
        List<String> payWayList = payService.initPay(payOrderNo);
        log.info("【{}】支付订单可用支付方式：【{}】", payOrderNo, payWayList);
        TestNg.attributes().set("payWayList", payWayList);
    }

    @Test(priority = 3)
    public void pay() {
        String payOrderNo = TestNg.attributes().get("payOrderNo", String.class);
        List<String> payWayList = (List<String>) TestNg.attributes().get("payWayList");
        Assert.assertTrue(CollectionUtils.hasElements(payWayList), "没有可用支付方式");
        String payWay = payWayList.get(0);
        payService.pay(payOrderNo, payWay);
        log.info("【{}】订单使用【{}】方式支付", payOrderNo, payWay);
    }
}
