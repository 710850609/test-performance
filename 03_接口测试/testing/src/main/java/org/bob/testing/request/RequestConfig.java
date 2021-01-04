package org.bob.testing.request;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RequestConfig {

    public static RequestConfig build() {
        return new RequestConfig();
    }

    private Map<String, String> headers = new HashMap<>();

    private String name;
    private String url;
    private String method;
    private Object body;

    public RequestConfig name(String name) {
        this.name = name;
        return this;
    }

    public RequestConfig url(String url) {
        this.url = url;
        return this;
    }
    public RequestConfig header(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public RequestConfig get() {
        method = "GET";
        return this;
    }
    public RequestConfig get(String url) {
        this.method = "GET";
        this.url = url;
        return this;
    }

    public RequestConfig post() {
        method = "POST";
        return this;
    }

    public RequestConfig put() {
        method = "PUT";
        return this;
    }

    public RequestConfig delete() {
        method = "DELETE";
        return this;
    }

    public RequestConfig body(Object s) {
        this.body = body;
        return this;
    }


}
