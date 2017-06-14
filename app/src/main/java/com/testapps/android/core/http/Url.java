package com.testapps.android.core.http;

import java.net.URLEncoder;
import java.util.HashMap;

public class Url
{
    private String baseUrl;
    private String paramStart;
    private String paramSeparator;
    private String paramKeyValueSeparator;
    private String method;
    private HashMap<String, String> params;

    public Url()
    {
        this.baseUrl = "";
        this.paramStart = "?";
        this.paramSeparator = "&";
        this.paramKeyValueSeparator = "=";
        this.method = "";

        this.params = new HashMap<String, String>();
    }

	/* Get / Set Methods */

    public void setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public void setParamStart(String paramStart)
    {
        this.paramStart = paramStart;
    }

    public void setParamSeparator(String paramSeparator)
    {
        this.paramSeparator = paramSeparator;
    }

    public void setParamKeyValueSeparator(String paramKeyValueSeparator)
    {
        this.paramKeyValueSeparator = paramKeyValueSeparator;
    }

    public void addParams(HashMap<String, String> params)
    {
        this.params.putAll(params);
    }

    public HashMap<String, String> getUrlParams()
    {
        return this.params;
    }

    @Override
    public String toString()
    {
        String urlString = "" + this.baseUrl + this.method + this.paramStart;

        for (String key : this.params.keySet())
        {
            urlString += key + this.paramKeyValueSeparator;

            if(this.params.get(key)!=null)
            {

                try
                {
                    urlString += URLEncoder.encode(this.params.get(key), "UTF-8");
                }
                catch(Exception e)
                {
                    urlString += this.params.get(key);
                }

            }
            else
            {
                urlString+=null;
            }

            urlString += this.paramSeparator;
        }

        return urlString;
    }
}
