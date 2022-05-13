package com.bob.test.service;

import com.bob.test.util.HttpUtil;
import org.springframework.stereotype.Component;

@Component
public class RefundService {

    public String apply(String payOrderNo) {
        HttpUtil.post("/applyRefund", null);
        return "201-" + System.currentTimeMillis();
    }

    public boolean check(String refundNo) {
        HttpUtil.post("/checkRefund", null);
        return false;
    }
}
