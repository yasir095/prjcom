package com.demoapp.android.comics;

import com.demoapp.android.comics.helper.Utils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by yasirmahmood on 17/07/2017.
 */

public class Connect
{
    @Inject Config config;
    @Inject Utils utils;

    private ComicApp app;

    public Connect(ComicApp app) {
        ((ComicApp) app).getComponent().inject(this);
    }

    private Map<String, String> generateDefaultParams()
    {
        String ts = utils.getCurrentTimestamp();

        HashMap<String, String> params = new HashMap<>();
        params.put("apikey", config.getPublicApiKey());
        params.put("ts", ts);
        params.put("hash", utils.md5(ts+config.getPrivateApiKey()+config.getPublicApiKey()));
        params.put("limit", "100");

        return params;
    }


}
