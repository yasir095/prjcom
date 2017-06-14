package com.testapps.android.core.parser;

import com.testapps.android.core.extras.XError;

import java.util.ArrayList;

/**
 * Created by yasirmahmood on 12/06/2017.
 */

public interface ParserDelegate<T>
{
    public void onParseComplete(T data);
    public void onParseFail(Error error);
}
