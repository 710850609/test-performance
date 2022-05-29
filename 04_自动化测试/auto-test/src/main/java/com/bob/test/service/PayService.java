package com.bob.test.service;

import com.apifan.common.random.source.NumberSource;
import com.bob.test.service.dto.UnifiedOrderReq;
import com.bob.test.service.dto.UnifiedOrderResp;
import com.bob.test.util.PayClient;
import org.springframework.stereotype.Component;
import org.testng.collections.Maps;

import java.util.Arrays;
import java.util.List;

@Component
public class PayService {

    public UnifiedOrderResp unifiedOrder(UnifiedOrderReq req) {
        PayClient.post("/unifiedOrder", Maps.newHashMap());
        String payOrderNo = "102-" + NumberSource.getInstance().randomInt(1000000, 9999999);
        return UnifiedOrderResp.builder()
                .payOrderNo(payOrderNo)
                .orderNo(req.getOrderNo())
                .address(req.getAddress())
                .build();
    }

    public List<String> initPay(String payOrderNo) {
        PayClient.post("/initPay", Maps.newHashMap());
        return Arrays.asList("微信", "支付宝", "云闪付");
    }

    public void pay(String orderNo, String payWay) {
        PayClient.post("/pay", Maps.newHashMap());
    }

    public boolean query(String payOrderNo) {
        PayClient.post("/query", null);
        return true;
    }
}
