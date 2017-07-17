package com.demoapp.android.core.http.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yasirmahmood on 12/06/2017.
 */

public class RequestBuilder
{
    public enum Method
    {
        GET(0), POST(1), PUT(2), DELETE(3), HEAD(4), OPTIONS(5), TRACE(6), PATCH(7);

        private int value;

        Method(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    private String tag;
    private Map<String, String> params;

    /**
     *
     * @param context , not application context
     * @param method
     * @param url
     * @param callback
     */
    public RequestBuilder(Context context, Method method, String url, final HttpResponseDelegate callback, final Map<String, String> headers, Map<String, String> params)
    {
        this.tag = "HttpReq".concat(Long.toString(System.currentTimeMillis()));
        this.params = params;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(method.getValue(), url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        callback.onError(error);
                    }
                })
        {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError
            {
                return RequestBuilder.this.params;
            };

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();

                if(super.getHeaders() != null && super.getHeaders().size()>0)
                {
                    params.putAll(super.getHeaders());
                }

                if(headers!=null && headers.size()>0)
                {
                    params.putAll(headers);
                }

                return params;
            }
        };

        stringRequest.setTag(tag); //generate an autoTag
        VolleyHttpRequest.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public String getTag(){
        return this.tag;
    }

    public void setTag(String tag){
        this.tag = tag;
    }

    public void cancelRequest(Context context)
    {
        VolleyHttpRequest.getInstance(context.getApplicationContext()).getRequestQueue().cancelAll(this.tag);
    }
}
