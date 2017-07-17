package com.demoapp.android.comics;

import android.app.Application;

import com.demoapp.android.comics.dagger.component.ApplicationComponent;
import com.demoapp.android.comics.dagger.component.DaggerApplicationComponent;
import com.demoapp.android.comics.dagger.module.ApiModule;
import com.demoapp.android.comics.dagger.module.ApplicationModule;

/**
 * Created by yasirmahmood on 17/07/2017.
 */

public class ComicApp extends Application
{
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule(new Config(Config.Environment.BETA), this))
                .build();
    }

    public ApplicationComponent getComponent()
    {
        return applicationComponent;
    }
}
