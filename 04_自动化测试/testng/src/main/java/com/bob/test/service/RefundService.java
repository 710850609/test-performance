package com.bob.test.service;

import com.bob.test.util.PayClient;
import org.springframework.stereotype.Component;

@Component
public class RefundService {

    public String apply(String payOrderNo) {
        PayClient.post("/applyRefund", null);
        return "201-" + System.currentTimeMillis();
    }

    public boolean check(String refundNo) {
        PayClient.post("/checkRefund", null);
        return false;
    }
}
