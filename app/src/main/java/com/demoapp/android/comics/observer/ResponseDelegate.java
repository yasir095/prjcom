package com.demoapp.android.comics.observer;

/**
 * Created by yasirmahmood on 17/07/2017.
 */

public interface ResponseDelegate<T>
{
    public void onSuccess(T value);
    public void onError(Throwable error);
}
