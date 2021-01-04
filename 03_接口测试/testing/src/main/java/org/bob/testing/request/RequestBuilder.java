package org.bob.testing.request;

import org.apache.http.HttpResponse;

public interface RequestBuilder {

    RequestConfig config();

    void handle(HttpResponse response);
}
