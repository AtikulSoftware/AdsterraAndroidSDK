package com.devatikul.sample;

import android.app.Application;
import com.devatikul.adsterrasdk.AdsterraSDK;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AdsterraSDK.init(this);
        AdsterraSDK.getInstance().setDebugMode(true);
    }
}