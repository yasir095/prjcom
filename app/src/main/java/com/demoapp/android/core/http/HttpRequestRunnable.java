package com.demoapp.android.core.http;

import java.net.URL;
import java.util.HashMap;

public class HttpRequestRunnable implements Runnable
{
    private URL url;
    private HttpRequest httpRequest;
    private String requestType;
    private HashMap<String, String> httpPostParams;
    private HashMap<String, String> headers;

    public HttpRequestRunnable(String urlString, HttpRequestDelegate delegate, HttpRequest httpRequest){

        try
        {
            this.url = new URL(urlString);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.httpRequest = httpRequest;
        this.httpRequest.setDelegate(delegate);
    }

    public void setPostParams(HashMap<String, String> httpPostParams)
    {
        this.httpPostParams = httpPostParams;
    }

    @Override
    public void run()
    {
        if(requestType != null && requestType == "POST")
        {
            httpRequest.post(url, httpPostParams);
        }
        else
        {
            httpRequest.get(url, headers);
        }

    }

    public void setHeaders(HashMap<String, String> headers)
    {
        this.headers = headers;
    }

    public void setRequestType(String requestType)
    {
        this.requestType = requestType;
    }

    public void cancelRequest()
    {
        this.httpRequest.cancelRequest();
        Thread.currentThread().interrupt();
    }
}
