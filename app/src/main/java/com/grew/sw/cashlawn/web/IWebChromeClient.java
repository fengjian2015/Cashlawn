package com.grew.sw.cashlawn.web;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class IWebChromeClient extends WebChromeClient {
    private ProgressBar webLoading;

    public IWebChromeClient(ProgressBar webLoading) {
        this.webLoading = webLoading;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        webLoading.setVisibility(View.VISIBLE);
        webLoading.setProgress(newProgress);
        if (newProgress >= 85) {
            webLoading.setVisibility(View.GONE);
        }
    }
}
