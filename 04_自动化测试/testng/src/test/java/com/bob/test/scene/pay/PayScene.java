package com.bob.test.scene.pay;

import com.bob.test.service.PayService;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * 支付场景
 */
public class PayScene {

    private PayService payService = new PayService();

    @DataProvider(name = "createOrderNo")
    private Object[][] createOrderNo() {
        Object[][] data = new Object[1][1];
        data[0][0] = String.valueOf(System.currentTimeMillis());
        return data;
    }

    @Test(dataProvider = "createOrderNo")
    public void unifiedOrder(String orderNo) {
        String payOrderNo = payService.unifiedOrder(orderNo);
    }

    @Test
    public List<String> initPay(String payOrderNo) {
        payService.initPay(payOrderNo);
        return null;
    }

    @Test
    public void pay(String orderNo, String payWay) {
        payService.pay(orderNo, payWay);
    }
}
