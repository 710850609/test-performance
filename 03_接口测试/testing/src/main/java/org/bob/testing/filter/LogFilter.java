package org.bob.testing.filter;

import org.apache.http.HttpResponse;
import org.bob.testing.request.RequestConfig;

public class LogFilter implements ExecuteFilter {

    private Long startTime;

    @Override
    public void beforeRequest(RequestConfig request) {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void afterResponse(RequestConfig request, HttpResponse response) {
        System.out.println(String.format("[%S]耗时： %d毫秒", request.getName(), (System.currentTimeMillis() - startTime)));
    }
}
