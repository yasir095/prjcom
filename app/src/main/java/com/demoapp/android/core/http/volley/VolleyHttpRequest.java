package com.demoapp.android.core.http.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by yasirmahmood on 12/06/2017.
 */

public class VolleyHttpRequest
{
    private static VolleyHttpRequest mInstance;
    private Context mContext;
    private RequestQueue mRequestQueue;

    /**
     * Application context here.
     * @param context
     */
    private VolleyHttpRequest(Context context)
    {
        this.mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyHttpRequest getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new VolleyHttpRequest(context);
        }

        return mInstance;
    }

    public com.android.volley.RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null)
        {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        this.getRequestQueue().add(req);
    }
}
