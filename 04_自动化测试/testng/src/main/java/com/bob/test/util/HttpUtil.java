package com.bob.test.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class HttpUtil {

    public static void post(String uri, Map<Object, Object> params) {
        uri = TestNg.attributes().get("host", String.class, "") + uri;
        log.info("POST请求:{}", uri);
    }
}
