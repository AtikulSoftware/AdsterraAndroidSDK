package com.devatikul.adsterrasdk.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.devatikul.adsterrasdk.AdSize;
import com.devatikul.adsterrasdk.AdsterraSDK;
import com.devatikul.adsterrasdk.R;
import com.devatikul.adsterrasdk.callback.AdLoadCallback;
import com.devatikul.adsterrasdk.internal.AdsterraLog;

public class AdsterraBannerView extends FrameLayout {
    private static final String SCRIPT_DOMAIN = "www.highperformanceformat.com";

    private WebView webView;
    private String adUnitKey;
    private AdSize adSize = AdSize.BANNER_320x50;
    private AdLoadCallback adLoadCallback;

    public AdsterraBannerView(Context context) {
        super(context);
        init();
    }

    public AdsterraBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttrs(context, attrs);
        init();
    }

    public AdsterraBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(context, attrs);
        init();
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AdsterraBannerView);
        try {
            String key = ta.getString(R.styleable.AdsterraBannerView_adUnitKey);
            if (key != null) adUnitKey = key;
            int index = ta.getInt(R.styleable.AdsterraBannerView_adSize, 0);
            AdSize[] values = AdSize.values();
            if (index >= 0 && index < values.length) adSize = values[index];
        } finally {
            ta.recycle();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        webView = new WebView(getContext());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportMultipleWindows(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setWebViewClient(new WebViewClient());

        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        addView(webView, lp);
    }

    public void setAdUnitKey(String adUnitKey) {
        this.adUnitKey = adUnitKey;
    }

    public void setAdSize(AdSize adSize) {
        this.adSize = adSize;
    }

    public void setAdListener(AdLoadCallback callback) {
        this.adLoadCallback = callback;
    }

    public void loadAd() {
        if (!AdsterraSDK.getInstance().isAdsEnabled()) {
            setVisibility(View.GONE);
            AdsterraLog.d("Ads are disabled globally. Skipping banner load.");
            return;
        }

        if (adUnitKey == null || adUnitKey.trim().isEmpty()) {
            if (adLoadCallback != null) {
                adLoadCallback.onAdFailedToLoad("Ad unit key is not set. Call setAdUnitKey() before loadAd().");
            }
            return;
        }

        setVisibility(View.VISIBLE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                AdsterraLog.d("Banner ad loaded: " + adUnitKey);
                if (adLoadCallback != null) adLoadCallback.onAdLoaded();
            }
        });

        webView.loadDataWithBaseURL(
                "https://" + SCRIPT_DOMAIN + "/",
                buildBannerHtml(adUnitKey, adSize),
                "text/html",
                "UTF-8",
                null
        );
    }

    private String buildBannerHtml(String key, AdSize size) {
        return "<!DOCTYPE html><html><head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<style>html,body{margin:0;padding:0;width:100%;overflow:hidden;background:transparent;}" +
                "#container{width:100%;display:flex;justify-content:center;align-items:center;}</style>" +
                "</head><body><div id=\"container\">" +
                "<script>var atOptions={'key':'" + key + "','format':'iframe'," +
                "'height':" + size.getHeight() + ",'width':" + size.getWidth() + ",'params':{}};</script>" +
                "<script src=\"https://" + SCRIPT_DOMAIN + "/" + key + "/invoke.js\"></script>" +
                "</div></body></html>";
    }

    public void destroy() {
        if (webView != null) {
            webView.destroy();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        destroy();
        super.onDetachedFromWindow();
    }
}
