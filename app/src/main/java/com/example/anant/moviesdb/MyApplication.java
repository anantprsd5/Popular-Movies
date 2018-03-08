package com.example.anant.moviesdb;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.stetho.Stetho;

/**
 * Created by anant on 5/3/18.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //To initialize Stetho for enabling the Android Debug Bridge
        Stetho.initializeWithDefaults(this);
    }
}
