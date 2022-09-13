package com.grew.sw.cashlawn.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsflyer.AppsFlyerLib;
import com.grew.sw.cashlawn.App;
import com.grew.sw.cashlawn.R;
import com.grew.sw.cashlawn.model.UrlResponse;
import com.grew.sw.cashlawn.model.UserInfoResponse;
import com.grew.sw.cashlawn.network.NetCallback;
import com.grew.sw.cashlawn.network.NetClient;
import com.grew.sw.cashlawn.network.NetErrorModel;
import com.grew.sw.cashlawn.network.NetUtil;
import com.grew.sw.cashlawn.util.ComUtil;
import com.grew.sw.cashlawn.util.ConsUtil;
import com.grew.sw.cashlawn.util.DateUtil;
import com.grew.sw.cashlawn.util.LoadingUtil;
import com.grew.sw.cashlawn.util.SparedUtils;
import com.grew.sw.cashlawn.util.ToastUtils;
import com.grew.sw.cashlawn.util.UserInfoUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class Sign1Activity extends AppCompatActivity {
    private TextView num_in1_et, num_in2_et, num_in3_et, num_in4_et;
    private EditText etCode;
    private TextView tvCodeHint, tvResend, tvCodeTime;
    private LinearLayout llCodeTime;
    private ImageView topBack;

    //秒
    private long codeTime = 60;
    private long smsSendTime;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (tvCodeTime != null) {
                long currentTime = DateUtil.getServerTimestamp() / 1000;
                long l = smsSendTime - currentTime;
                tvCodeTime.setText(""+l);
                if (l <=0){
                    sendCodeView(true);
                    return;
                }
                handler.sendEmptyMessage(1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign1);
        initBar();
        initView();
        sendCode();
    }


    private void initView() {
        num_in1_et = findViewById(R.id.num_in1_et);
        num_in2_et = findViewById(R.id.num_in2_et);
        num_in3_et = findViewById(R.id.num_in3_et);
        num_in4_et = findViewById(R.id.num_in4_et);
        etCode = findViewById(R.id.etCode);
        tvCodeTime = findViewById(R.id.tvCodeTime);
        llCodeTime = findViewById(R.id.llCodeTime);
        tvCodeHint = findViewById(R.id.tvCodeHint);
        tvResend = findViewById(R.id.tvResend);
        topBack = findViewById(R.id.topBack);

        etCode.setFocusable(true);
        etCode.setFocusableInTouchMode(true);
        etCode.requestFocus();

        String phoneNumber = SignActivity.phoneNumber;
        if (phoneNumber.length() == 10) {
            phoneNumber = phoneNumber.substring(7);
            tvCodeHint.setText("xxxxx" + phoneNumber);
        }

        etCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        etCode.addTextChangedListener(new TextChangeLister());
        tvResend.setOnClickListener(view -> sendCode());
        topBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void initBar() {
        ImmersionBar
                .with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.common_bg)
                .statusBarDarkFont(true)
                .keyboardEnable(true)
                .init();
    }


    //自定义文本变化监听类
    class TextChangeLister implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int length = s.length();
            //字符串内容存入数组
            char[] c = new char[4];
            c = s.toString().toCharArray();
            if (c.length >= 1) {
                num_in1_et.setText(c[0] + "");
            } else {
                num_in1_et.setText("");
            }
            if (c.length >= 2) {
                num_in2_et.setText(c[1] + "");
            } else {
                num_in2_et.setText("");
            }
            if (c.length >= 3) {
                num_in3_et.setText(c[2] + "");
            } else {
                num_in3_et.setText("");
            }
            if (c.length >= 4) {
                num_in4_et.setText(c[3] + "");
            } else {
                num_in4_et.setText("");
            }
            num_in1_et.setTextColor(Color.parseColor("#fff69800"));
            num_in2_et.setTextColor(Color.parseColor("#fff69800"));
            num_in3_et.setTextColor(Color.parseColor("#fff69800"));
            num_in4_et.setTextColor(Color.parseColor("#fff69800"));
            if (length == 0) {
                num_in1_et.setBackground(getResources().getDrawable(R.drawable.shape_code_true));
                num_in2_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
                num_in3_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
                num_in4_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
            } else if (length == 1) {
                num_in1_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
                num_in2_et.setBackground(getResources().getDrawable(R.drawable.shape_code_true));
                num_in3_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
                num_in4_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
            } else if (length == 2) {
                num_in1_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
                num_in2_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
                num_in3_et.setBackground(getResources().getDrawable(R.drawable.shape_code_true));
                num_in4_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
            } else if (length == 3) {
                num_in1_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
                num_in2_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
                num_in3_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
                num_in4_et.setBackground(getResources().getDrawable(R.drawable.shape_code_true));
            } else if (length == 4) {
                num_in1_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
                num_in2_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
                num_in3_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
                num_in4_et.setBackground(getResources().getDrawable(R.drawable.shape_code_false));
                num_in1_et.setTextColor(Color.parseColor("#F6625B"));
                num_in2_et.setTextColor(Color.parseColor("#F6625B"));
                num_in3_et.setTextColor(Color.parseColor("#F6625B"));
                num_in4_et.setTextColor(Color.parseColor("#F6625B"));
                login();
            }
        }
    }


    private void sendCode() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", SignActivity.phoneNumber);
        NetClient.getNewService()
                .sendCode(map)
                .compose(NetUtil.applySchedulers())
                .doOnSubscribe(disposable ->
                        LoadingUtil.show())
                .doFinally(() -> {
                    LoadingUtil.dismiss();
                })
                .subscribe(new NetCallback<UserInfoResponse>() {
                    @Override
                    public void businessFail(NetErrorModel netErrorModel) {
                        ToastUtils.showLong(netErrorModel.getMessage());
                        sendCodeView(true);
                    }

                    @Override
                    public void businessSuccess(UserInfoResponse d) {
                        if (d != null && d.getCode() == 200) {
                            smsSendTime = DateUtil.getServerTimestamp() / 1000 + codeTime;
                            handler.sendEmptyMessage(1);
                            sendCodeView(false);
                        } else {
                            ToastUtils.showLong(d.getMessage());
                            sendCodeView(true);
                        }
                    }
                });
    }

    private void login(){
        Map<String, String> map = new HashMap<>();
        map.put("Phone", SignActivity.phoneNumber);
        map.put("Code", etCode.getText().toString());
        map.put("deviceModel", Build.MODEL);
        map.put("mobilePhoneBrands", Build.BRAND);
        map.put("appMarketCode", "Google");
        map.put("appsFlyerId", AppsFlyerLib.getInstance().getAppsFlyerUID(App.get()));
        map.put("channelCode", SparedUtils.getString(ConsUtil.KEY_AF_CHANNEL));
        NetClient.getNewService()
                .login(map)
                .compose(NetUtil.applySchedulers())
                .doOnSubscribe(disposable ->
                        LoadingUtil.show())
                .doFinally(() -> {
                    LoadingUtil.dismiss();
                })
                .subscribe(new NetCallback<UserInfoResponse>() {
                    @Override
                    public void businessFail(NetErrorModel netErrorModel) {
                        ToastUtils.showLong(netErrorModel.getMessage());
                        etCode.setText("");
                    }

                    @Override
                    public void businessSuccess(UserInfoResponse d) {
                        if (d != null && d.getCode() == 200 && d.getData() != null) {
                            UserInfoResponse.Data data = d.getData();
                            data.setMobileNumber(SignActivity.phoneNumber);
                            data.setAppVersion(ComUtil.getVersionName());
                            data.setDevName("android");
                            UserInfoUtil.save(d.getData());
                            Intent intent=new Intent("loginSuccess");
                            LocalBroadcastManager.getInstance(Sign1Activity.this).sendBroadcast(intent);
                            WebActivity.openWeb(Sign1Activity.this,true,d.getData().getHomeUrl());
                            etCode.postDelayed(() -> Sign1Activity.this.finish(),500);
                        } else {
                            ToastUtils.showLong(d.getMessage());
                            etCode.setText("");
                        }
                    }
                });
    }

    private void sendCodeView(boolean isFail) {
        if (isFail) {
            llCodeTime.setVisibility(View.GONE);
            tvResend.setVisibility(View.VISIBLE);
        } else {
            llCodeTime.setVisibility(View.VISIBLE);
            tvResend.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.post(() -> handler.removeMessages(1));
    }
}