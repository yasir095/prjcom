package com.demoapp.android.core.extras;

import android.util.Log;

/**
 * Created by yasirmahmood on 11/06/2017.
 */

public class Logger {

    private String Env = "production";

    public static void logSuccess(String message)
    {
        Log.d("SUCCESS", message);
    }

    public static void logInfo(String message)
    {
        Log.i("Info", message);
    }

    public static void logError(String message)
    {
        Log.e("Error", message);
    }
}
