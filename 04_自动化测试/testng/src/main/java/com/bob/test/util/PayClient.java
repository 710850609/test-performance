package com.bob.test.util;

import com.bob.test.core.TestNg;
import lombok.CustomLog;

import java.util.Map;

/**
 * 支付客户端
 */
@CustomLog
public class PayClient {

    private static String getRequestUri(String uri) {
        // 获取环境变量中配置的支付服务域名
        return TestNg.env().get("pay.host", String.class, "") + uri;
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
