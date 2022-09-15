package com.grew.sw.cashlawn.view;

import static com.grew.sw.cashlawn.util.ConsUtil.HOME_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.grew.sw.cashlawn.R;
import com.grew.sw.cashlawn.model.UrlResponse;
import com.grew.sw.cashlawn.model.VersionResponse;
import com.grew.sw.cashlawn.network.NetCallback;
import com.grew.sw.cashlawn.network.NetClient;
import com.grew.sw.cashlawn.network.NetErrorModel;
import com.grew.sw.cashlawn.network.NetUtil;
import com.grew.sw.cashlawn.util.ComUtil;
import com.grew.sw.cashlawn.util.ConsUtil;
import com.grew.sw.cashlawn.util.SparedUtils;
import com.grew.sw.cashlawn.view.cus.UpdateDialog;
import com.grew.sw.cashlawn.web.IWebSetting;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

public class SignActivity extends AppCompatActivity {
    private ImageView topBack;
    private EditText etNumber;
    private TextView tvLine, ptv;
    private CheckBox pcb;
    private Button btUpgrade;
    public static String phoneNumber;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initBar();
        initView();
        initData();
        initWebView();
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent!=null){
                    String action = intent.getAction();
                    if ("loginSuccess".equals(action)){
                        SignActivity.this.finish();
                    }
                }
            }
        }, new IntentFilter( "loginSuccess"));
    }

    private void initWebView() {
        webView = new WebView(this);
        IWebSetting.init(webView);
        webView.loadUrl(HOME_URL);
    }

    private void initData() {
        NetClient.getNewService()
                .getUrl()
                .compose(NetUtil.applySchedulers())
                .subscribe(new NetCallback<UrlResponse>() {
                    @Override
                    public void businessFail(NetErrorModel netErrorModel) {

                    }

                    @Override
                    public void businessSuccess(UrlResponse d) {
                        if (d != null && d.getCode() == 200 && d.getData() != null) {
                            List<UrlResponse.Data> data = d.getData();
                            for (int i= 1;i<=data.size(); i++){
                                if (i == 1){
                                    SparedUtils.putString(ConsUtil.KEY_URL_1,data.get(i-1).url);
                                }else if (i == 6){
                                    SparedUtils.putString(ConsUtil.KEY_URL_2,data.get(i-1).url);
                                }
                            }

                        }
                    }
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


    private void initView() {
        etNumber = findViewById(R.id.etNumber);
        topBack = findViewById(R.id.topBack);
        tvLine = findViewById(R.id.tvLine);
        pcb = findViewById(R.id.pcb);
        ptv = findViewById(R.id.ptv);
        btUpgrade = findViewById(R.id.btUpgrade);
        etNumber.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
        checkButton();
        String ptvTxt = ptv.getText().toString();
        SpannableString spannableString = new SpannableString(ptvTxt);
        tColorTextClick(spannableString, 30, 47, Color.parseColor("#F69800"), view -> {
            if (SparedUtils.getString(ConsUtil.KEY_URL_1) == null) return;
            WebActivity.openWeb(this,false,SparedUtils.getString(ConsUtil.KEY_URL_1));
        });
        tColorTextClick(spannableString, 49, ptvTxt.length(), Color.parseColor("#F69800"), view -> {
            if (SparedUtils.getString(ConsUtil.KEY_URL_2) == null) return;
            WebActivity.openWeb(this,false,SparedUtils.getString(ConsUtil.KEY_URL_2));
        });
        ptv.setText(spannableString);
        ptv.setMovementMethod(LinkMovementMethod.getInstance());

        topBack.setOnClickListener(view -> finish());
        etNumber.setOnFocusChangeListener((view, b) -> {
            if (b) {
                tvLine.setBackgroundColor(Color.parseColor("#F69800"));
            } else {
                tvLine.setBackgroundColor(Color.parseColor("#979797"));
            }
        });
        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etNumber.getText().toString().startsWith("0")){
                    etNumber.setText("");
                    return;
                }
                if (etNumber.getText().toString().contains(" ")) {
                    String txt = etNumber.getText().toString().replaceAll(" ", "");
                    etNumber.setText(txt);
                    etNumber.setSelection(txt.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkButton();
            }
        });

        pcb.setOnCheckedChangeListener((compoundButton, b) -> {
            checkButton();
        });

        btUpgrade.setOnClickListener(view -> {
            phoneNumber = etNumber.getText().toString();
            startActivity(new Intent(this,Sign1Activity.class));
        });
    }

    private void checkButton() {
        String s = etNumber.getText().toString();
        if (TextUtils.isEmpty(s)){
            etNumber.setTextSize( TypedValue.COMPLEX_UNIT_SP,13);
        }else {
            etNumber.setTextSize( TypedValue.COMPLEX_UNIT_SP,16);
        }
        if (!pcb.isChecked()) {
            btUpgrade.setBackground(getResources().getDrawable(R.drawable.shape_button_later));
            btUpgrade.setClickable(false);
            btUpgrade.setEnabled(false);
            return;
        }
        if (TextUtils.isEmpty(etNumber.getText()) || etNumber.getText().length() != 10) {
            btUpgrade.setBackground(getResources().getDrawable(R.drawable.shape_button_later));
            btUpgrade.setClickable(false);
            btUpgrade.setEnabled(false);
            return;
        }
        btUpgrade.setBackground(getResources().getDrawable(R.drawable.shape_button_upgrade));
        btUpgrade.setClickable(true);
        btUpgrade.setEnabled(true);
    }

    public static SpannableString tColorTextClick(SpannableString style,int start, int end, int color, View.OnClickListener onClickListener){
        style.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onClickListener.onClick(widget);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(color);
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }

    @Override
    protected void onDestroy() {
        IWebSetting.clearWebView(webView);
        super.onDestroy();
    }
}