package com.testapps.android.core.resultset;

import android.content.Context;

import com.android.volley.VolleyError;
import com.testapps.android.core.http.volley.HttpResponseDelegate;
import com.testapps.android.core.http.volley.RequestBuilder;
import com.testapps.android.core.parser.ComicParser;
import com.testapps.android.core.parser.Parser;
import com.testapps.android.core.parser.ParserDelegate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yasirmahmood on 11/06/2017.
 */

public abstract class ResultSet<T> implements HttpResponseDelegate, ParserDelegate
{
    private enum State {
        INITIALISED, FETCHING, PARSING, COMPLETE, FAIL
    };

    protected State state;
    protected Parser<T> parser;
    protected ResultSetDelegate delegate;

    protected Context applicationContext;
    protected RequestBuilder.Method method;
    protected String requestUrl;
    protected Map<String, String> postParams;
    protected Map<String, String> headers;

    protected RequestBuilder requestBuilder;


    public ResultSet() {
        this.state = State.INITIALISED;
        this.postParams = new HashMap<>();
    }

    public Parser<T> getParser() {
        return parser;
    }

    public void setParser(Parser<T> parser)
    {
        this.parser = parser;
    }

    public ResultSetDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(ResultSetDelegate delegate) {
        this.delegate = delegate;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public RequestBuilder.Method getMethod() {
        return method;
    }

    public void setMethod(RequestBuilder.Method method) {
        this.method = method;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Map<String, String> getPostParams() {
        return postParams;
    }

    public void setPostParams(Map<String, String> postParams) {
        this.postParams = postParams;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public ResultSet<T> fetch(ResultSetDelegate delegate)
    {
        this.delegate = delegate;

        if (this.state != State.FETCHING) {
            this.state = State.FETCHING;

            this.requestBuilder = new RequestBuilder(this.applicationContext, this.method, this.requestUrl, this, this.headers, this.postParams);
        }

        return this;
    }

    public void cancel()
    {
        if(this.state == State.FETCHING)
        {
            this.requestBuilder.cancelRequest(this.applicationContext);
        }
    }

    public void resetState(){
        this.state = State.INITIALISED;
    }

    @Override
    public void onSuccess(String response)
    {
        this.state = State.PARSING;
        this.parser.getParsedData(response, this);
    }

    @Override
    public void onError(VolleyError error)
    {
        this.state = State.FAIL;

        if(error.networkResponse == null)
        {
            this.delegate.onFailResult(this, 503);
        }
        else
        {
            this.delegate.onFailResult(this, error.networkResponse.statusCode);
        }
    }

    @Override
    public void onParseComplete(Object data)
    {
        this.state = State.COMPLETE;
        this.setParsedData((T) data);
        this.delegate.onCompleteResult(this);
    }

    @Override
    public void onParseFail(Error error)
    {
        this.state = State.FAIL;
        //@todo: implements statusCode response here.
        this.delegate.onFailResult(this, 0);
    }

    protected abstract void setParsedData(T parsedData);
    public abstract T getParsedData();
}
