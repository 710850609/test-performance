package com.bob.test.cases.junit;

import com.alibaba.fastjson.JSON;
import com.apifan.common.random.source.AreaSource;
import com.apifan.common.random.source.NumberSource;
import com.bob.test.core.Tests;
import com.bob.test.service.PayService;
import com.bob.test.service.dto.UnifiedOrderReq;
import com.bob.test.service.dto.UnifiedOrderResp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.collections.CollectionUtils;

import java.util.List;

/**
 * 支付用例
 * @author BOB
 */
@SpringBootTest
@DisplayName("支付-退款")
public class Pay {

    @Autowired
    private PayService payService;

    @Test
    @DisplayName("统一下单")
    public void unifiedOrder() {
        String orderNo = "101-" + NumberSource.getInstance().randomInt(1000000, 9999999);
        String address = AreaSource.getInstance().randomAddress();
        UnifiedOrderReq req = new UnifiedOrderReq(orderNo, address);
        UnifiedOrderResp resp = payService.unifiedOrder(req);
        Tests.logger(this).info("下支付单：{}", JSON.toJSONString(resp));
        String payOrderNo = resp.getPayOrderNo();
        Assertions.assertNotEquals(payOrderNo, null, "下支付单失败");
        Tests.attributes().set("payOrderNo", payOrderNo);
    }

    @Test
    @DisplayName("获取支付方式")
    public void initPay() {
        String payOrderNo = Tests.attributes().get("payOrderNo", String.class);
        List<String> payWayList = payService.initPay(payOrderNo);
        Tests.logger(this).info("【{}】支付订单可用支付方式：【{}】", payOrderNo, payWayList);
        Tests.attributes().set("payWayList", payWayList);
    }

    @Test
    @DisplayName("确认支付")
    public void pay() {
        String payOrderNo = Tests.attributes().get("payOrderNo", String.class);
        List<String> payWayList = (List<String>) Tests.attributes().get("payWayList");
        Assertions.assertTrue(CollectionUtils.hasElements(payWayList), "没有可用支付方式");
        String payWay = payWayList.get(0);
        payService.pay(payOrderNo, payWay);
        Tests.logger(this).info("【{}】订单使用【{}】方式支付", payOrderNo, payWay);
    }

    @Test
    @DisplayName("查询支付结果")
    public void query() {
        String payOrderNo = Tests.attributes().get("payOrderNo", String.class);
        boolean result = payService.query(payOrderNo);
        Tests.logger(this).info("【{}】订单支付结果：{}", payOrderNo, result);
    }
}
