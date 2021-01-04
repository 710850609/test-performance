package org.bob.testing.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtil {

    private static final CloseableHttpClient CLIENT;
    static {
        SSLConnectionSocketFactory socketFactory = SSLConnectionSocketFactory.getSocketFactory();
        ConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", plainSocketFactory)
                .register("https", socketFactory)
                .build();
        RequestConfig config = RequestConfig.custom()
                // 等待从连接池返回连接时间 1s
                .setConnectionRequestTimeout(1000)
                // 确定连接建立前的超时时间 1s
                .setConnectTimeout(1000)
                // 等待数据返回时间 -1 s
                .setSocketTimeout(-1)
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        // 默认每个路由最大连接数 500
        connectionManager.setDefaultMaxPerRoute(1024);
        // 连接池最大连接数 500
        connectionManager.setMaxTotal(1024);
        // 连接空闲7秒后，检查连接有效性
        connectionManager.setValidateAfterInactivity(3000);
        connectionManager.closeIdleConnections(1, TimeUnit.MINUTES);
        CLIENT = HttpClients.custom()
                // 设置代理，可用于模拟弱网情况
//				.setProxy(new org.apache.http.HttpHost("localhost", 8888, "https"))
//                    .setSSLSocketFactory(sslSocketFactory) // 设置过 ConnectionManager，SSLSocketFactory设置无效
                .setDefaultRequestConfig(config)
                .setConnectionManager(connectionManager)
                .build();
    }

    public static HttpResponse get(String url, Map<String, String> headers, Object body) throws URISyntaxException, IOException {
        HttpGet request = new HttpGet();
        request.setURI(new URI(url));
        if (headers != null) {
            headers.forEach((k, v) -> {
                if (v != null) {
                    request.setHeader(k, v);
                }
            });
        }
        return CLIENT.execute(request);
    }
    public static HttpResponse post(String url, Map<String, String> headers, Object body) throws URISyntaxException, IOException {
        HttpPost request = new HttpPost();
        request.setURI(new URI(url));
        if (headers != null) {
            headers.forEach((k, v) -> {
                if (v != null) {
                    request.setHeader(k, v);
                }
            });
        }
        if (body != null) {
            StringEntity httpEntity = new StringEntity(body.toString(), "UTF-8");
            request.setEntity(httpEntity);
        }
        return CLIENT.execute(request);
    }

}
