package com.demoapp.android.comics.dagger.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.demoapp.android.comics.helper.Utils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yasirmahmood on 17/07/2017.
 */

@Module
public class ApplicationModule
{
    private Application application;

    public ApplicationModule(Application application)
    {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideAppContext()
    {
        return application;
    }

    @Provides @Singleton
    public SharedPreferences provideSharedPrefs(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides @Singleton
    public Utils provideUtils()
    {
        return new Utils();
    }
}
