package com.testapps.android.core;

import android.content.Context;

import com.testapps.android.core.cache.ImageCache;
import com.testapps.android.core.http.AsyncTaskExecutor;
import com.testapps.android.core.http.Url;
import com.testapps.android.core.http.volley.RequestBuilder;
import com.testapps.android.core.parser.ComicParser;
import com.testapps.android.core.resultset.ComicResultSet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by yasirmahmood.
 */

public class Broker
{
    private static Broker defaultBroker;
    private AsyncTaskExecutor asyncTaskExecutor;
    private ImageCache imageCache;

    public Broker()
    {

    }

    public static Broker getDefaultBroker()
    {
        if(defaultBroker == null)
        {
            defaultBroker = new Broker();
        }

        return defaultBroker;
    }

    private HashMap<String, String> getDefaultParams()
    {
        String ts = getCurrentTimestamp();

        HashMap<String, String> params = new HashMap<>();
        params.put("apikey", Config.publicApiKey);
        params.put("ts", ts);
        params.put("hash", md5(ts+Config.privateApiKey+Config.publicApiKey));
        params.put("limit", "100");

        return params;
    }

    private String getCurrentTimestamp()
    {
        Long time = System.currentTimeMillis()/1000;
        return time.toString();
    }

    public ComicResultSet getComics(Context applicationContext)
    {
        //ts=1&apikey=1234&hash=ffd275c5130566a2916217b101f26150

        Url url = new Url();
        url.setBaseUrl(Config.baseUrl);
        url.setMethod("/v1/public/comics");
        url.addParams(getDefaultParams());

        ComicResultSet resultSet = new ComicResultSet();
        resultSet.setApplicationContext(applicationContext);
        resultSet.setMethod(RequestBuilder.Method.GET);
        resultSet.setRequestUrl(url.toString());
        resultSet.setParser(new ComicParser());

        return resultSet;
    }

    public AsyncTaskExecutor getDefaultAsyncTask()
    {
        if(this.asyncTaskExecutor == null)
        {
            this.asyncTaskExecutor = new AsyncTaskExecutor();
        }

        return this.asyncTaskExecutor;
    }

    public static ImageCache getDefaultImageCache(int cacheSize)
    {
        if(Broker.getDefaultBroker().imageCache == null)
        {
            Broker.defaultBroker.imageCache = new ImageCache(cacheSize);
        }

        return Broker.defaultBroker.imageCache;
    }

    //*** not implemented by me. *******
    private String md5(final String str)
    {
        final String MD5 = "MD5";

        try
        {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();

            for (byte aMessageDigest : messageDigest)
            {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }

            return hexString.toString();

        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return "";
    }

}
