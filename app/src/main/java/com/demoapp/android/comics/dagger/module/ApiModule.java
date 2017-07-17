package com.demoapp.android.comics.dagger.module;

import com.demoapp.android.comics.ComicApp;
import com.demoapp.android.comics.Connect;
import com.demoapp.android.comics.api.RequestInterceptor;
import com.demoapp.android.comics.Config;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yasirmahmood on 17/07/2017.
 */

@Module
public class ApiModule
{
    private Config config;
    private ComicApp app;

    public ApiModule(Config config, ComicApp app) {
        this.config = config;
        this.app = app;
    }

    @Provides @Singleton
    public Config getConfig()
    {
        return this.config;
    }

    @Provides @Singleton
    public ComicApp provideApplication()
    {
        return this.app;
    }

    @Provides
    @Singleton
    public Connect provideConnect(ComicApp app)
    {
        return new Connect(app);
    }

    @Provides
    @Singleton
    public Gson provideGson()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY); //LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides @Singleton
    public OkHttpClient.Builder provideOkHttpClient()
    {
        return new OkHttpClient.Builder();
    }

    @Provides @Singleton
    public RequestInterceptor provideRequestInterceptor()
    {
        return new RequestInterceptor();
    }

    @Provides @Singleton
    public Retrofit provideRetrofitInstance(RequestInterceptor requestInterceptor)
    {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // add session headers interceptor
        httpClient.addInterceptor(requestInterceptor);

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is for logging remove in production.

        return new Retrofit.Builder()
                .baseUrl(getConfig().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
    }
}
