package com.devatikul.adsterrasdk.callback;

import com.devatikul.adsterrasdk.smartlink.SmartlinkCloseReason;

public interface SmartlinkAdCallback {
    void onSmartlinkOpened();
    void onSmartlinkClosed(SmartlinkCloseReason reason);
    void onSmartlinkFailed(String errorMessage);
}
