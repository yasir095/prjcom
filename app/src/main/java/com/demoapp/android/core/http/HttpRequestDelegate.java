package com.demoapp.android.core.http;

import com.demoapp.android.core.extras.XError;

public interface HttpRequestDelegate
{
    public void onCompleteHttpRequest(String result);
    public void onFailedHttpRequest(XError e);
    public void onProgressHttpRequest(int progress);
}
