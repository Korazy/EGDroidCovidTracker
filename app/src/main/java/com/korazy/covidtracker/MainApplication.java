package com.korazy.covidtracker;

import android.app.Application;
import android.content.Context;

import com.androidnetworking.AndroidNetworking;

public class MainApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
