package com.grew.sw.cashlawn.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.grew.sw.cashlawn.R;
import com.grew.sw.cashlawn.util.SpecialPermissionUtil;
import com.grew.sw.cashlawn.util.UserInfoUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBar();
        initData();
    }

    private void initData() {
        if (XXPermissions.isGranted(this,
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION,
                Permission.CAMERA,
                Permission.READ_PHONE_STATE,
                Permission.READ_SMS,
                Permission.READ_CONTACTS,
                Permission.GET_ACCOUNTS,
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.READ_CALL_LOG)) {
            if (!SpecialPermissionUtil.isLocServiceEnable() || !SpecialPermissionUtil.isOpenWifi()) {
                startPermission();
                return;
            }
            if (UserInfoUtil.getUserInfo() == null) {
                startActivity(new Intent(this, SignActivity.class));
                finish();
            } else {
                WebActivity.openWeb(this, true,UserInfoUtil.getHomeUrl());
                finish();
            }
        } else {
            startPermission();
        }
    }

    private void startPermission(){
        startActivity(new Intent(this, AgreementActivity.class));
        finish();
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
}