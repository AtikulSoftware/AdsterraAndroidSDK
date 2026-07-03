package com.devatikul.sample;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.devatikul.adsterrasdk.banner.AdsterraBannerView;
import com.devatikul.adsterrasdk.callback.AdLoadCallback;
import com.devatikul.adsterrasdk.callback.SmartlinkAdCallback;
import com.devatikul.adsterrasdk.smartlink.AdsterraSmartlinkAd;
import com.devatikul.adsterrasdk.smartlink.SmartlinkCloseReason;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // load banner ads
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

        Button btnAction = findViewById(R.id.btnUserAction);
        btnAction.setOnClickListener(v -> {
            AdsterraSmartlinkAd smartlinkAd = new AdsterraSmartlinkAd.Builder()
                    .setSmartlinkUrl("https://www.effectivecpmnetwork.com/t181icqfd?key=babfc2115a114ba72a5579634ce03a87")
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
    } // onCreate bundle end here ================


} // public class end here =======================