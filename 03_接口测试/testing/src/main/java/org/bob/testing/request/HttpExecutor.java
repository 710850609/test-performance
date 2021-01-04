package org.bob.testing.request;

import org.apache.http.HttpResponse;
import org.bob.testing.filter.ExecuteFilter;
import org.bob.testing.filter.LogFilter;
import org.bob.testing.filter.ReportFilter;
import org.bob.testing.util.HttpUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class HttpExecutor implements Runnable {

    private HttpExecutor() {}

    private RequestBuilder config;

    private RequestConfig request;

    private HttpResponse response;

    private List<ExecuteFilter> filters;

    private CountDownLatch countDownLatch;


    public static HttpExecutor build(RequestBuilder config) {
        HttpExecutor executor = new HttpExecutor();
        executor.config = config;
        executor.filters = Arrays.asList(new LogFilter(), new ReportFilter());
        return executor;

    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        this.request = this.config.config();
        this.filters.forEach(filter -> {
           filter.beforeRequest(this.request);
        });
        String method = this.request.getMethod();
        try {
            switch (method) {
                    case "GET":
                    this.response = HttpUtil.get(this.request.getUrl(), this.request.getHeaders(), this.request.getBody());
                    break;
                case "POST":
                    this.response = HttpUtil.post(this.request.getUrl(), this.request.getHeaders(), this.request.getBody());
                    break;
                default:
                    throw new RuntimeException("暂不支持请求方法：" + method);
            }
            this.config.handle(response);
            for (int i = this.filters.size(); i >0 ; i--) {
                ExecuteFilter filter = this.filters.get(i - 1);
                filter.afterResponse(this.request, this.response);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.countDownLatch.countDown();
        }
    }

}
