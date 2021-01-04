package org.bob.testing.request;

import org.apache.http.HttpResponse;


public interface ResponseHandler {

    void handle(HttpResponse response);

}
