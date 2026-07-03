package com.devatikul.adsterrasdk.callback;

public interface AdLoadCallback {
    void onAdLoaded();
    void onAdFailedToLoad(String errorMessage);
}
