package org.bob.testing.filter;

import org.apache.http.HttpResponse;
import org.bob.testing.request.RequestConfig;

public interface ExecuteFilter {

    void beforeRequest(RequestConfig request);

    void afterResponse(RequestConfig request, HttpResponse response);

}
