package com.demoapp.android.comics.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yasirmahmood on 17/07/2017.
 */

public class RequestInterceptor implements Interceptor
{
    private Map<String, String> defaultHeaders;

    public RequestInterceptor()
    {
        this.defaultHeaders = new HashMap<>();
    }

    public void setDefaultHeaders(Map<String, String> headers)
    {
        this.defaultHeaders = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();

        Request.Builder requestBuilder = request.newBuilder();

        for (String key: this.defaultHeaders.keySet())
        {
            requestBuilder.addHeader(key, this.defaultHeaders.get(key));
        }

        return chain.proceed(requestBuilder.build());
    }
}
