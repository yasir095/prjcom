package com.testapps.android.core.http;

import com.testapps.android.core.extras.XError;

import java.io.IOException;

public interface HttpRequestDelegate
{
    public void onCompleteHttpRequest(String result);
    public void onFailedHttpRequest(XError e);
    public void onProgressHttpRequest(int progress);
}
