package com.korazy.covidtracker;

import android.app.Application;
import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.blongho.country_data.World;

public class MainApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
        World.init(getApplicationContext());
        mContext = this;

    }

    public static Context getContext(){
        return mContext;
    }
}
