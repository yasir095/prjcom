package com.demoapp.android.core.http.volley;

import com.android.volley.VolleyError;

/**
 * Created by yasirmahmood on 12/06/2017.
 */

public interface HttpResponseDelegate
{
    public void onSuccess(String response);
    public void onError(VolleyError error);
}
