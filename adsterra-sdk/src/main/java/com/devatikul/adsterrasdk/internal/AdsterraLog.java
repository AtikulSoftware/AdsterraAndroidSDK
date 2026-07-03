package com.devatikul.adsterrasdk.internal;

import android.util.Log;
import com.devatikul.adsterrasdk.AdsterraSDK;

public final class AdsterraLog {
    private static final String TAG = "AdsterraSDK";

    private AdsterraLog() {
    }

    public static void d(String message) {
        if (AdsterraSDK.getInstance().isDebugMode()) {
            Log.d(TAG, message);
        }
    }

    public static void e(String message, Throwable t) {
        if (AdsterraSDK.getInstance().isDebugMode()) {
            Log.e(TAG, message, t);
        }
    }
}
