package com.example.ainak;

import android.app.Application;

import androidx.multidex.BuildConfig;

import com.example.ainak.network.ApiClient;

import timber.log.Timber;

/**
 * Application Class for  Ainak
 */
public class Ainak extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Initializing Retrofit Instance
        ApiClient.Companion.initialize(this);

        //Initializing Timber for Debug
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
