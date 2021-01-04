package org.bob.testing;

import com.google.common.base.Preconditions;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.bob.testing.request.RequestBuilder;
import org.bob.testing.request.RequestConfig;
import org.bob.testing.util.RunContext;


public class LoginUnit {

    public static RequestBuilder login() {
        return new RequestBuilder(){
            @Override
            public RequestConfig config() {
                return RequestConfig.build()
                    .name("登录")
                    .get("https://www.baidu.com")
                    .header("cookie", "abc")
                    .body("{}");
            }

            @Override
            public void handle(HttpResponse response) {
                // 处理请求返回，包括断言、取值等等操作
                Preconditions.checkArgument(response.getStatusLine().getStatusCode() == 200, "状态码错误");
                Header[] cookies = response.getHeaders("Content-Type");
                RunContext.setGlobalValue("Content-Type", cookies[0].getValue());
                System.out.println(cookies[0].getValue());
            }
        };
    }

    public static RequestBuilder logout() {
        return new RequestBuilder() {
            @Override
            public RequestConfig config() {
                System.out.println(RunContext.getGlobalValue("Content-Type", String.class));
                return RequestConfig.build()
                        .name("登出")
                        .get("https://www.baidu.com")
                        .header("cookie", "abc")
                        .header("Content-Type", RunContext.getGlobalValue("Content-Type", String.class))
                        .body("{}");
            }

            @Override
            public void handle(HttpResponse response) {
                Preconditions.checkArgument(response.getStatusLine().getStatusCode() == 200, "状态码错误");
            }
        };
    }

}
