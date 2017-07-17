package com.demoapp.android.core.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by yasirmahmood.
 */

public abstract class Parser<T>
{
    public ObjectMapper mapper;

    public Parser()
    {
        this.mapper = new ObjectMapper(); // create once, reuse
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void getParsedData(final String jsonObject, final ParserDelegate delegate)
    {
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                T data = getParsedData(jsonObject);
                delegate.onParseComplete(data);
            }
        };

        runnable.run();
    }

    public abstract T getParsedData(String jsonTokenData);
}
