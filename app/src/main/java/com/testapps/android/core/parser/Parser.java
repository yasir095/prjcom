package com.testapps.android.core.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testapps.android.core.extras.XError;

import java.util.ArrayList;
import java.util.HashMap;

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
