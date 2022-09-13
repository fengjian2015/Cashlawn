package com.grew.sw.cashlawn.web;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.grew.sw.cashlawn.App;
import com.grew.sw.cashlawn.R;
import com.grew.sw.cashlawn.util.IActivityManager;
import com.grew.sw.cashlawn.util.LogUtils;


public class IWebViewClient extends WebViewClient {
    private boolean isLoadSuccess = true;
    private String mUrl;
    private TextView titleTextView;
    private ProgressBar webLoading;

    public IWebViewClient(TextView titleTextView,ProgressBar webLoading){
        this.titleTextView = titleTextView;
        this.webLoading = webLoading;
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        mUrl = url;
        LogUtils.d("onPageStarted->" + url);
        isLoadSuccess = true;
        webLoading.setVisibility(View.VISIBLE);
        webLoading.setProgress(0);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        final SslErrorHandler mHandler;
        mHandler = handler;
        AlertDialog.Builder builder = new AlertDialog.Builder(IActivityManager.getActivity());
        builder.setMessage(App.get().getString(R.string.webview_ssl_hint));
        builder.setPositiveButton(App.get().getString(R.string.webview_ssl_go), (dialog, which) -> mHandler.proceed());
        builder.setNegativeButton(App.get().getString(R.string.webview_ssl_cancel), (dialog, which) -> mHandler.cancel());
        builder.setOnKeyListener((dialog, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                mHandler.cancel();
                dialog.dismiss();
                return true;
            }
            return false;
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String s) {
        LogUtils.d("重定向:" + s);
        if (!TextUtils.isEmpty(s) && s.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(s));
            webView.getContext().startActivity(intent);
            return true;
        }
        try {
            if (TextUtils.isEmpty(s)) return false;
            webLoading.setVisibility(View.VISIBLE);
            webLoading.setProgress(0);
            String schem = Uri.parse(s).getScheme();
            if (schem != null && schem.contains("file")) return false;
            if (schem != null && (schem.contains("http") || schem.contains("https"))) {
                return false;
            }
        } catch (UnsupportedOperationException e) {
            return false;
        }
        try {
            webLoading.setVisibility(View.GONE);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(s));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            webView.getContext().startActivity(intent);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        webLoading.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(view.getTitle())){
            titleTextView.post(() -> titleTextView.setText(view.getTitle()));
            return;

        }
    }


    @Override
    public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
        super.onReceivedError(webView, errorCode, description, failingUrl);
        LogUtils.d("页面加载错误：111:::" + description + "::::" + failingUrl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return;
        }
        isLoadSuccess = false;
    }

    @Override
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        super.onReceivedError(webView, webResourceRequest, webResourceError);
        LogUtils.d("页面加载错误：222:::" + webResourceError.getDescription() + ":::" + webResourceRequest.getUrl());
        if (webResourceRequest.isForMainFrame()) {
            isLoadSuccess = false;
        }
    }

    @Override
    public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        try {
            if (webResourceRequest != null && webResourceRequest.isForMainFrame() && mUrl.equals(webResourceRequest.getUrl().toString()) && webResourceResponse.getStatusCode() != 403) {
                LogUtils.d("页面加载错误：333:::" + webResourceResponse.getStatusCode() + ",信息：" + webResourceResponse.getReasonPhrase() + mUrl.equals(webResourceRequest.getUrl().toString()));
                isLoadSuccess = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
