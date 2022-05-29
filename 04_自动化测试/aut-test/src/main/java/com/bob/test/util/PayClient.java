package com.bob.test.util;

import com.bob.test.core.Tests;
import lombok.CustomLog;

import java.util.Map;

/**
 * 支付客户端
 */
public class PayClient {

    private static String getRequestUri(String uri) {
        // 获取环境变量中配置的支付服务域名
        return Tests.env().get("pay.host", String.class, "") + uri;
    }

    public static void post(String uri, Map<Object, Object> params) {
        uri = getRequestUri(uri);
        Tests.logger(PayClient.class).info("POST请求:{}", uri);
    }

    public static void get(String uri, Map<Object, Object> params) {
        uri = getRequestUri(uri);
        Tests.logger(PayClient.class).info("GET请求:{}", uri);
    }
}
