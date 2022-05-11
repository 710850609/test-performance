package com.bob.test.service;

import com.bob.test.util.HttpUtil;
import org.testng.collections.Maps;

import java.util.List;

/**
 * 支付服务
 */
public class PayService {

    public String unifiedOrder(String orderNo) {
//        HttpUtil.post("/unifiedOrder", Maps.newHashMap());
        return System.currentTimeMillis() + "";
    }

    public List<String> initPay(String payOrderNo) {
        HttpUtil.post("/initPay", Maps.newHashMap());
        return null;
    }

    public void pay(String orderNo, String payWay) {
        HttpUtil.post("/pay", Maps.newHashMap());
    }

}
