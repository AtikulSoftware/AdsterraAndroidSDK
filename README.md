# Adsterra Android SDK

> Unofficial Android SDK for integrating Adsterra Banner and Smartlink (Direct Link) ads into Android applications.

[![Maven Central](https://img.shields.io/maven-central/v/io.github.atikulsoftware/adsterra-sdk.svg)](https://central.sonatype.com/artifact/io.github.atikulsoftware/adsterra-sdk)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Android](https://img.shields.io/badge/Android-API%2021%2B-brightgreen.svg)]()

## ✨ Features

- Banner Ads
- Smartlink (Direct Link)
- Lightweight
- Java Support
- Kotlin Support
- AndroidX Compatible
- API 21+

## Gradle (Groovy)
```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.atikulsoftware:adsterra-sdk:1.0.0")
}
```

## Kotlin DSL
```kotlin
dependencies {
    implementation("io.github.atikulsoftware:adsterra-sdk:1.0.0")
}
```

## Banner Example
```xml
    <com.devatikul.adsterrasdk.banner.AdsterraBannerView
        android:layout_alignParentBottom="true"
        android:id="@+id/bannerAdView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        app:adUnitKey=""
        app:adSize="banner_320x50" />
```

## Load Banner Ads With Callbacks
```java
        AdsterraBannerView bannerAdView = findViewById(R.id.bannerAdView);
        bannerAdView.setAdListener(new AdLoadCallback() {
            @Override
            public void onAdLoaded() {
                Log.d("Ads", "Banner loaded");
            }
            @Override
            public void onAdFailedToLoad(String errorMessage) {
                Log.e("Ads", "Banner failed: " + errorMessage);
            }
        });
        bannerAdView.loadAd();
```

## Smartlink Example
```java
        Button btnAction = findViewById(R.id.btnUserAction);
        btnAction.setOnClickListener(v -> {
            AdsterraSmartlinkAd smartlinkAd = new AdsterraSmartlinkAd.Builder()
                    .setSmartlinkUrl("Your Link")
                    .setDurationSeconds(15)
                    .build();

            smartlinkAd.setAdCallback(new SmartlinkAdCallback() {
                @Override
                public void onSmartlinkOpened() {
                    Log.d("Ads", "Smartlink opened");
                    Toast.makeText(MainActivity.this, "Smartlink opened", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSmartlinkClosed(SmartlinkCloseReason reason) {
                    if (reason == SmartlinkCloseReason.COMPLETED) {
                        Log.d("Ads", "User watched full ad");
                        Toast.makeText(MainActivity.this, "You have completed the advertisement.", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("Ads", "User cancelled ad early");
                        Toast.makeText(MainActivity.this, "You did not complete the advertisement.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSmartlinkFailed(String errorMessage) {
                    Log.e("Ads", "Smartlink failed: " + errorMessage);
                }
            });
            smartlinkAd.show(MainActivity.this);
        });
```


> [!IMPORTANT]
> **Disclaimer:** This library is an unofficial implementation and is **not affiliated with, endorsed by, or maintained by Adsterra**.
> All trademarks and brand names are the property of their respective owners.

## License
Licensed under the Apache License 2.0.

## Author
**Md. Atikul Islam**

GitHub:
https://github.com/AtikulSoftware

Website:
https://devatikul.com

## Support
If you like this project, please consider giving it a ⭐ on GitHub.