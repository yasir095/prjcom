package com.testapps.android.core.http;

import android.content.Context;
import android.os.Build;

import com.testapps.android.core.extras.Logger;
import com.testapps.android.core.extras.XError;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yasirmahmood on 10/06/2017.
 */

public class HttpRequest
{
    private HttpRequestDelegate delegate;
    private AtomicBoolean isInterrupted;
    private File directoryName;

    public HttpRequest()
    {
        disableConnectionReuseIfNecessary();
    }


    public void setDelegate(HttpRequestDelegate delegate)
    {
        this.delegate = delegate;
    }

    public void setCacheDir(File directoryName)
    {
        this.directoryName = directoryName;
    }

    /**
     * When no delegate set , return response as inputstream
     * @param url
     * @return
     */
    public InputStream getInputStream(URL url)
    {
        InputStream in = null;

        try
        {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try
            {
                in = new BufferedInputStream(urlConnection.getInputStream());
                return in;
            }
            finally
            {
                //urlConnection.disconnect();
                Logger.logSuccess(""+url);
            }

        }
        catch(Exception e)
        {
            Logger.logError(e.toString());
        }

        return in;

    }

    public InputStream get(URL url, HashMap<String, String> headers)
    {
        String result = null;
        InputStream in = null;
        HttpURLConnection urlConnection = null;

        if(delegate!=null)
        {
            try
            {
                urlConnection = (HttpURLConnection) url.openConnection();

                if(headers != null)
                {
                    for (String key : headers.keySet())
                    {
                        urlConnection.setRequestProperty(key, headers.get(key));
                    }
                }

                try
                {
                    in = new BufferedInputStream(urlConnection.getInputStream());
                    result = readStream(in);
                }
                finally
                {
                    urlConnection.disconnect();
                    Logger.logInfo(url+"");
                    delegate.onCompleteHttpRequest(result);
                }

            }
            catch(Exception e)
            {
                try
                {
                    XError error = new XError();
                    error.setStatusCode(urlConnection.getResponseCode());
                    delegate.onFailedHttpRequest(error);
                }
                catch (IOException e1)
                {
                    delegate.onFailedHttpRequest(null);
                }

                Logger.logError(e.toString());
            }
        }else{
            return getInputStream(url);
        }

        return null;
    }

    public void post(URL url, HashMap<String, String> params)
    {
        enableHttpResponseCache();
        String result = null;

        try
        {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try
            {
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setChunkedStreamingMode(0);

                DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());

                if(params!=null)
                {
                    writeStream(out, getParseUrlEncodeParams(params));
                }

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = readStream(in);
            }

            finally
            {
                urlConnection.disconnect();
                delegate.onCompleteHttpRequest(result);
            }
        }
        catch(Exception e)
        {
            delegate.onFailedHttpRequest(null);
        }

    }

    private String readStream(InputStream in) {

        StringBuffer result = new StringBuffer();

        try
        {
            BufferedReader reader = null;

            try
            {

                reader = new BufferedReader(new InputStreamReader(in));

                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    result.append(line);

                    if(isInterrupted.get())
                    {
                        delegate.onFailedHttpRequest(new XError());
                    }
                    else
                    {
                        delegate.onProgressHttpRequest(0);
                    }
                }

            }
            catch (IOException e)
            {

            }

            finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }catch(Exception exception){

        }
        if(result!=null){
            //delegate.onCompleteHttpRequest(result.toString());
            return result.toString();
        }else{
            return "";
        }

    }

    private void writeStream(DataOutputStream output, String params) {
        try {
            String content = null;
            output.writeBytes(content);
            output.flush();
            output.close();
        } catch (IOException ex) {

        }
    }

    private String getParseUrlEncodeParams(HashMap<String, String> params)
    {
        StringBuilder postParams = new StringBuilder();
        String key, value, result = "";

        for (Map.Entry<String, String> entry : params.entrySet())
        {
            key = entry.getKey();
            try
            {
                value =  URLEncoder.encode((String) entry.getValue(), "UTF-8");
                postParams.append(key+"="+value+"&");
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }

        result = postParams.toString();
        result = result.substring(0, result.length() - 1);
        return result;
    }

    private void enableHttpResponseCache()
    {
        if(this.directoryName!= null)
        {
            try
            {
                long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
                File httpCacheDir = new File(this.directoryName, "http");
                Class.forName("android.net.http.HttpResponseCache")
                        .getMethod("install", File.class, long.class)
                        .invoke(null, httpCacheDir, httpCacheSize);
            }
            catch (Exception httpResponseCacheNotAvailable)
            {

            }
        }
    }

    private void disableConnectionReuseIfNecessary()
    {
        // HTTP connection reuse which was buggy pre-froyo
        if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO)
        {
            System.setProperty("http.keepAlive", "false");
        }
    }

    public void cancelRequest()
    {
        this.isInterrupted = new AtomicBoolean(true);
    }
}
