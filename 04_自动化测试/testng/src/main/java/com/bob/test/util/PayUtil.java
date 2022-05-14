package com.bob.test.util;

import com.bob.test.core.TestNg;
import lombok.CustomLog;

import java.util.Map;

@CustomLog
public class PayUtil {

    private static String getRequestUri(String uri) {
        return TestNg.attributes().get("pay.host", String.class, "") + uri;
    }

    public static void post(String uri, Map<Object, Object> params) {
        uri = getRequestUri(uri);
        log.info("POST请求:{}", uri);
    }

    public static void get(String uri, Map<Object, Object> params) {
        uri = getRequestUri(uri);
        log.info("GET请求:{}", uri);
    }
}
