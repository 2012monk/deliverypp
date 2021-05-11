package com.deli.deliverypp.util;

import java.lang.reflect.Method;

public class UriSources {

    private Method method;
    private String uri;
    private boolean isProtected;

    public UriSources() {
    }

    public UriSources(Method method, String uri, boolean isProtected) {
        this.method = method;
        this.uri = uri;
        this.isProtected = isProtected;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }
}
