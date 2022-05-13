package com.bob.test.service;

import com.bob.test.util.HttpUtil;
import org.springframework.stereotype.Component;
import org.testng.collections.Maps;

import java.util.Arrays;
import java.util.List;

@Component
public class PayService {

    public String unifiedOrder(String orderNo, String appType) {
        HttpUtil.post("/unifiedOrder", Maps.newHashMap());
        return "102-" + System.currentTimeMillis();
    }

    public List<String> initPay(String payOrderNo) {
        HttpUtil.post("/initPay", Maps.newHashMap());
        return Arrays.asList("微信", "支付宝", "云闪付");
    }

    public void pay(String orderNo, String payWay) {
        HttpUtil.post("/pay", Maps.newHashMap());
    }

}
