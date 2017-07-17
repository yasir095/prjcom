package com.demoapp.android.comics.dagger.component;

import com.demoapp.android.comics.ComicApp;
import com.demoapp.android.comics.Connect;
import com.demoapp.android.comics.view.MainActivity;
import com.demoapp.android.comics.dagger.module.ApiModule;
import com.demoapp.android.comics.dagger.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yasirmahmood on 17/07/2017.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent
{
    void inject(ComicApp target);
    void inject(MainActivity target);
    void inject(Connect target);
}