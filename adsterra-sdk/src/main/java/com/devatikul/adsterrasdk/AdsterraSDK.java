package com.devatikul.adsterrasdk;

import android.content.Context;

import com.devatikul.adsterrasdk.internal.AdsterraPrefs;

public final class AdsterraSDK {
    private static volatile AdsterraSDK instance;

    private final Context appContext;
    private boolean adsEnabled = true;
    private boolean debugMode = false;

    private AdsterraSDK(Context context) {
        this.appContext = context.getApplicationContext();
    }

    public static void init(Context context) {
        if (instance == null) {
            synchronized (AdsterraSDK.class) {
                if (instance == null) {
                    instance = new AdsterraSDK(context);
                    AdsterraPrefs.init(instance.appContext);
                    instance.adsEnabled = AdsterraPrefs.isAdsEnabled();
                }
            }
        }
    }

    public static AdsterraSDK getInstance() {
        if (instance == null) {
            throw new IllegalStateException(
                    "AdsterraSDK is not initialized. Call AdsterraSDK.init(context) first, " +
                            "preferably inside your Application class."
            );
        }
        return instance;
    }

    public Context getContext() {
        return appContext;
    }

    public void setAdsEnabled(boolean enabled) {
        this.adsEnabled = enabled;
        AdsterraPrefs.setAdsEnabled(enabled);
    }

    public boolean isAdsEnabled() {
        return adsEnabled;
    }

    public void setDebugMode(boolean debug) {
        this.debugMode = debug;
    }

    public boolean isDebugMode() {
        return debugMode;
    }
}