package com.devatikul.adsterrasdk.smartlink;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.devatikul.adsterrasdk.R;
import com.devatikul.adsterrasdk.callback.SmartlinkAdCallback;

public class SmartlinkActivity extends AppCompatActivity {
    public static final String EXTRA_URL = "com.devatikul.adsterrasdk.EXTRA_URL";
    public static final String EXTRA_DURATION_SECONDS = "com.devatikul.adsterrasdk.EXTRA_DURATION_SECONDS";

    static SmartlinkAdCallback pendingCallback;

    private WebView webView;
    private ProgressBar progressBar;
    private TextView countdownText;
    private ImageButton btnClose;
    private CountDownTimer countDownTimer;
    private boolean alreadyFinished = false;
    private SmartlinkAdCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_smartlink);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        callback = pendingCallback;
        pendingCallback = null;

        String url = getIntent().getStringExtra(EXTRA_URL);
        int duration = getIntent().getIntExtra(EXTRA_DURATION_SECONDS, 15);

        webView = findViewById(R.id.adsterraWebView);
        progressBar = findViewById(R.id.adsterraProgressBar);
        countdownText = findViewById(R.id.adsterraCountdownText);
        btnClose = findViewById(R.id.adsterraBtnClose);

        setupWebView();

        if (url == null || url.trim().isEmpty()) {
            if (callback != null) callback.onSmartlinkFailed("Smartlink URL is empty");
            finish();
            return;
        }

        webView.loadUrl(url);
        if (callback != null) callback.onSmartlinkOpened();

        btnClose.setOnClickListener(v -> finishSmartlink(SmartlinkCloseReason.CANCELLED));
        startCountdown(duration);

    } // onCreate bundle end here =====================

    private void setupWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void startCountdown(int totalSeconds) {
        countDownTimer = new CountDownTimer(totalSeconds * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = (millisUntilFinished / 1000) + 1;
                countdownText.setText(getString(R.string.adsterra_countdown_format, secondsLeft));
            }

            @Override
            public void onFinish() {
                finishSmartlink(SmartlinkCloseReason.COMPLETED);
            }
        };
        countDownTimer.start();
    }

    private void finishSmartlink(SmartlinkCloseReason reason) {
        if (alreadyFinished) return;
        alreadyFinished = true;
        if (callback != null) callback.onSmartlinkClosed(reason);
        finish();
    }

    @SuppressLint({"MissingSuperCall", "GestureBackNavigation"})
    @Override
    public void onBackPressed() {
        finishSmartlink(SmartlinkCloseReason.CANCELLED);
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) countDownTimer.cancel();
        if (webView != null) webView.destroy();
        super.onDestroy();
    }

} // public class end here ============================