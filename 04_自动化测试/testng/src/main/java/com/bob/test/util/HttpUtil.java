package com.bob.test.util;

import lombok.CustomLog;

import java.util.Map;

@CustomLog
public class HttpUtil {
    public static void post(String uri, Map<Object, Object> params) {
        uri = TestNg.attributes().get("host", String.class, "") + uri;
        log.info("POST请求:{}", uri);
    }
}
