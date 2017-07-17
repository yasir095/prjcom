package com.demoapp.android.core.resultset;

/**
 * Created by yasirmahmood on 12/06/2017.
 */

public interface ResultSetDelegate
{
    public void onCompleteResult(ResultSet resultSet);
    public void onFailResult(ResultSet resultSet, int statusCode);
}
