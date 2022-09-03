package com.grew.sw.cashlawn.view;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.grew.sw.cashlawn.R;
import com.grew.sw.cashlawn.util.BatteryReceiver;
import com.grew.sw.cashlawn.util.SoftKeyboardUtils;
import com.grew.sw.cashlawn.util.UpdateUtil;
import com.grew.sw.cashlawn.web.IWebChromeClient;
import com.grew.sw.cashlawn.web.IWebSetting;
import com.grew.sw.cashlawn.web.IWebViewClient;
import com.grew.sw.cashlawn.web.WebJsBridge;
import com.gyf.immersionbar.ImmersionBar;

public class WebActivity extends AppCompatActivity {

    private static final String IS_HOME = "IS_HOME";
    private static final String WEB_URL = "WEB_URL";
    private boolean isHome = false;
    private String webUrl;
    private WebJsBridge webJsBridge;
    private FrameLayout topFrame;
    private ImageView topBack;
    private TextView topTitle;
    private ProgressBar webLoading;
    private WebView webView;
    private BatteryReceiver batteryReceiver;

    public static void openWeb(Activity activity, boolean isHome,String url) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra(IS_HOME, isHome);
        intent.putExtra(WEB_URL,url);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webUrl = getIntent().getStringExtra(WEB_URL);
        isHome = getIntent().getBooleanExtra(IS_HOME,false);
        initView();
        initBar();
        initData();
        initBattery();
    }

    private void initBattery() {
        if (isHome) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
            batteryReceiver = new BatteryReceiver();
            registerReceiver(batteryReceiver, intentFilter);
        }
    }

    @Override
    protected void onDestroy() {
        if (isHome){
            unregisterReceiver(batteryReceiver);
        }
        super.onDestroy();
    }

    private void initBar() {
        if (isHome){
            ImmersionBar
                    .with(this)
                    .statusBarDarkFont(true)
                    .keyboardEnable(true)
                    .init();
        }else {
            ImmersionBar
                    .with(this)
                    .fitsSystemWindows(true)
                    .statusBarColor(R.color.common_bg)
                    .statusBarDarkFont(true)
                    .keyboardEnable(true)
                    .init();
        }
    }

    private void initView() {
        topFrame = findViewById(R.id.topFrame);
        topBack = findViewById(R.id.topBack);
        topTitle = findViewById(R.id.topTitle);
        webLoading = findViewById(R.id.webLoading);
        webView = findViewById(R.id.webView);

    }

    private void initData() {
        webJsBridge = new WebJsBridge(this, webView);
        webView.addJavascriptInterface(webJsBridge,"appAndroid");
        IWebSetting.init(webView);
        webView.setWebViewClient(new IWebViewClient(topTitle,webLoading));
        webView.setWebChromeClient(new IWebChromeClient(webLoading));
        if (isHome){
            UpdateUtil.checkUpdate(this);
            topFrame.setVisibility(View.GONE);
        }else {
            topFrame.setVisibility(View.VISIBLE);
        }

        topBack.setOnClickListener(view -> {
            checkFinish();
        });
//        测试
        webUrl = "file:///android_asset/h5.html";
        if (!webUrl.startsWith("http") && !webUrl.startsWith("file")){
            webUrl = "https://"+webUrl;
        }
        webView.loadUrl(webUrl);
    }

    private void checkFinish(){
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if (isHome){
                moveTaskToBack(true);
            }else {
                finish();
            }
            SoftKeyboardUtils.hideSoftKeyboard(this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            checkFinish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        webJsBridge.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}