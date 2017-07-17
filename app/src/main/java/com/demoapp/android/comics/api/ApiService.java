package com.demoapp.android.comics.api;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by yasirmahmood on 17/07/2017.
 */

public interface ApiService<T>
{
    @GET("/v1/public/comics")
    Observable<HashMap<String,Object>> getComics();
}
