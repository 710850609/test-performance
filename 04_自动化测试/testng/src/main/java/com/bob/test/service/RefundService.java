package com.bob.test.service;

import com.bob.test.util.PayUtil;
import org.springframework.stereotype.Component;

@Component
public class RefundService {

    public String apply(String payOrderNo) {
        PayUtil.post("/applyRefund", null);
        return "201-" + System.currentTimeMillis();
    }

    public boolean check(String refundNo) {
        PayUtil.post("/checkRefund", null);
        return false;
    }
}
