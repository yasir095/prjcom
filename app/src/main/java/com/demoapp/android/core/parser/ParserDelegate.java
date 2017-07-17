package com.demoapp.android.core.parser;

/**
 * Created by yasirmahmood on 12/06/2017.
 */

public interface ParserDelegate<T>
{
    public void onParseComplete(T data);
    public void onParseFail(Error error);
}
