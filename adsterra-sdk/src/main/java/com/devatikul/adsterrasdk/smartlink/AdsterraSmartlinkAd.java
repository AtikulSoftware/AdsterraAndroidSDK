package com.devatikul.adsterrasdk.smartlink;

import android.app.Activity;
import android.content.Intent;

import com.devatikul.adsterrasdk.AdsterraSDK;
import com.devatikul.adsterrasdk.callback.SmartlinkAdCallback;
import com.devatikul.adsterrasdk.internal.AdsterraLog;

public class AdsterraSmartlinkAd {
    private final String smartlinkUrl;
    private final int durationSeconds;
    private SmartlinkAdCallback callback;

    private AdsterraSmartlinkAd(Builder builder) {
        this.smartlinkUrl = builder.smartlinkUrl;
        this.durationSeconds = builder.durationSeconds;
    }

    public void setAdCallback(SmartlinkAdCallback callback) {
        this.callback = callback;
    }

    public void show(Activity activity) {
        if (!AdsterraSDK.getInstance().isAdsEnabled()) {
            AdsterraLog.d("Ads are disabled globally. Skipping Smartlink.");
            if (callback != null) callback.onSmartlinkClosed(SmartlinkCloseReason.CANCELLED);
            return;
        }
        SmartlinkActivity.pendingCallback = callback;
        Intent intent = new Intent(activity, SmartlinkActivity.class);
        intent.putExtra(SmartlinkActivity.EXTRA_URL, smartlinkUrl);
        intent.putExtra(SmartlinkActivity.EXTRA_DURATION_SECONDS, durationSeconds);
        activity.startActivity(intent);
    }

    public static class Builder {
        private String smartlinkUrl;
        private int durationSeconds = 15;

        public Builder setSmartlinkUrl(String url) {
            this.smartlinkUrl = url;
            return this;
        }

        public Builder setDurationSeconds(int seconds) {
            this.durationSeconds = seconds;
            return this;
        }

        public AdsterraSmartlinkAd build() {
            if (smartlinkUrl == null || smartlinkUrl.trim().isEmpty()) {
                throw new IllegalArgumentException("Smartlink URL must be set via setSmartlinkUrl().");
            }
            return new AdsterraSmartlinkAd(this);
        }
    }
}
