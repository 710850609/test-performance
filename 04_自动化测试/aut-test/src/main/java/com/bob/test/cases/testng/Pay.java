package com.bob.test.cases.testng;

import com.alibaba.fastjson.JSON;
import com.apifan.common.random.source.AreaSource;
import com.apifan.common.random.source.NumberSource;
import com.bob.test.core.Tests;
import com.bob.test.service.PayService;
import com.bob.test.service.dto.UnifiedOrderReq;
import com.bob.test.service.dto.UnifiedOrderResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.collections.CollectionUtils;

import java.util.List;

/**
 * 支付用例
 * @author BOB
 */
@SpringBootTest
public class Pay extends AbstractTestNGSpringContextTests {

    @Autowired
    private PayService payService;

    @Test(description = "统一下单", priority = 1)
    public void unifiedOrder() {
        String orderNo = "101-" + NumberSource.getInstance().randomInt(1000000, 9999999);
        String address = AreaSource.getInstance().randomAddress();
        UnifiedOrderReq req = new UnifiedOrderReq(orderNo, address);
        UnifiedOrderResp resp = payService.unifiedOrder(req);
        Tests.logger(this).info("下支付单：{}", JSON.toJSONString(resp));
        String payOrderNo = resp.getPayOrderNo();
        Assert.assertNotEquals(payOrderNo, null, "下支付单失败");
        Tests.attributes().set("payOrderNo", payOrderNo);
    }

    @Test(description = "获取支付方式", priority = 2)
    public void initPay() {
        String payOrderNo = Tests.attributes().get("payOrderNo", String.class);
        List<String> payWayList = payService.initPay(payOrderNo);
        Tests.logger(this).info("【{}】支付订单可用支付方式：【{}】", payOrderNo, payWayList);
        Tests.attributes().set("payWayList", payWayList);
    }

    @Test(description = "确认支付", priority = 3)
    public void pay() {
        String payOrderNo = Tests.attributes().get("payOrderNo", String.class);
        List<String> payWayList = (List<String>) Tests.attributes().get("payWayList");
        Assert.assertTrue(CollectionUtils.hasElements(payWayList), "没有可用支付方式");
        String payWay = payWayList.get(0);
        payService.pay(payOrderNo, payWay);
        Tests.logger(this).info("【{}】订单使用【{}】方式支付", payOrderNo, payWay);
    }

    @Test(description = "查询支付结果", priority = 4, dependsOnMethods = {"pay"})
    public void query() {
        String payOrderNo = Tests.attributes().get("payOrderNo", String.class);
        boolean result = payService.query(payOrderNo);
        Tests.logger(this).info("【{}】订单支付结果：{}", payOrderNo, result);
    }
}
