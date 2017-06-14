package com.testapps.android.core.extras;

/**
 * Created by yasirmahmood on 11/06/2017.
 */

public class XError extends Error {

    private int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
