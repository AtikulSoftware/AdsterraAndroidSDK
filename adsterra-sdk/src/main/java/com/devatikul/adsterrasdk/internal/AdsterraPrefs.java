package com.devatikul.adsterrasdk.internal;

import android.content.Context;
import android.content.SharedPreferences;

public final class AdsterraPrefs {
    private static final String PREF_NAME = "adsterra_sdk_prefs";
    private static final String KEY_ADS_ENABLED = "ads_enabled";

    private static SharedPreferences prefs;

    private AdsterraPrefs() {
    }

    public static void init(Context context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    public static boolean isAdsEnabled() {
        return prefs == null || prefs.getBoolean(KEY_ADS_ENABLED, true);
    }

    public static void setAdsEnabled(boolean enabled) {
        if (prefs != null) {
            prefs.edit().putBoolean(KEY_ADS_ENABLED, enabled).apply();
        }
    }
}
