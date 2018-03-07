package com.wja.myapplication;

import android.app.Application;

/**
 * Created by hanke on 2018/2/12.
 */

public class MyApplication extends Application{
    private static MyApplication context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }
    public static MyApplication getContext(){
        return context;
    }
}
