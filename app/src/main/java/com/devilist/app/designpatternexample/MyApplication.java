package com.devilist.app.designpatternexample;

import android.app.Application;
import android.content.Context;

/**
 * Created by zengpu on 2017/4/1.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
