package com.proverbio.android.spring.context;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Juan Pablo Proverbio
 */
public class TodoApplication extends Application
{
    @Override
    public void onCreate()
    {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("todome.realm")
                .schemaVersion(1)
                .build();

        // Set the default Realm configuration at the beginning.
        Realm.setDefaultConfiguration(realmConfiguration);

        super.onCreate();
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }
}
