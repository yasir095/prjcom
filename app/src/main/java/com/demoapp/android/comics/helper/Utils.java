package com.demoapp.android.comics.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yasirmahmood.
 */

public class Utils
{
    public Utils()
    {

    }

    public String getCurrentTimestamp()
    {
        Long time = System.currentTimeMillis()/1000;
        return time.toString();
    }

//    public ComicResultSet getComics(Context applicationContext)
//    {
//        //ts=1&apikey=1234&hash=ffd275c5130566a2916217b101f26150
//
//        Url url = new Url();
//        url.setBaseUrl(Config.baseUrl);
//        url.setMethod("/v1/public/comics");
//        url.addParams(getDefaultParams());
//
//        ComicResultSet resultSet = new ComicResultSet();
//        resultSet.setApplicationContext(applicationContext);
//        resultSet.setMethod(RequestBuilder.Method.GET);
//        resultSet.setRequestUrl(url.toString());
//        resultSet.setParser(new ComicParser());
//
//        return resultSet;
//    }

    //*** not implemented by me. *******
    public String md5(final String str)
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
