package com.grew.sw.cashlawn.web;


import android.os.Build;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class IWebSetting {
    public static void init(WebView webView) {
        try {
            WebSettings webSettings = webView.getSettings();
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setDisplayZoomControls(true);
            webSettings.setDatabaseEnabled(true);
            webSettings.setSavePassword(false);
            webSettings.setDomStorageEnabled(true);
            webSettings.setFantasyFontFamily("fantasy");
            webSettings.setFixedFontFamily("monospace");
            webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setLightTouchEnabled(false);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setLoadsImagesAutomatically(true);
            webSettings.setTextZoom(100);
            webSettings.setMediaPlaybackRequiresUserGesture(true);
            webSettings.setSansSerifFontFamily("sans-serif");
            webSettings.setSaveFormData(true);
            webSettings.setSavePassword(false);
            webSettings.setSerifFontFamily("serif");
            webSettings.setStandardFontFamily("sans-serif");
            webSettings.setSupportMultipleWindows(false);
            webSettings.setSupportZoom(true);
            webSettings.setAppCacheEnabled(true);
            webSettings.setAllowContentAccess(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            webSettings.setBlockNetworkImage(false);
            webSettings.setBlockNetworkLoads(false);
            webSettings.setBuiltInZoomControls(false);
            webSettings.setCursiveFontFamily("cursive");
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setEnableSmoothTransition(false);
            webSettings.setGeolocationEnabled(true);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            if (Build.VERSION.SDK_INT >= 21) {
                cookieManager.setAcceptThirdPartyCookies(webView, true);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
        } catch (Throwable e) {
        }
    }

    public static synchronized void clearWebView(WebView webview) {
        if (webview == null) return;
        try {
            if (webview.getParent() != null) {
                ((ViewGroup) webview.getParent()).removeView(webview);
            }
            webview.stopLoading();
            webview.getSettings().setJavaScriptEnabled(false);
            webview.clearHistory();
            webview.clearView();
            webview.removeAllViews();
            webview.destroy();
        } catch (Throwable e) {
        }

    }

}
