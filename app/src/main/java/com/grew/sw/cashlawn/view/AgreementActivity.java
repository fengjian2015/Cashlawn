package com.grew.sw.cashlawn.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.grew.sw.cashlawn.R;
import com.grew.sw.cashlawn.util.SpecialPermissionUtil;
import com.grew.sw.cashlawn.util.UserInfoUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

public class AgreementActivity extends AppCompatActivity {

    private ImageView topBack;
    private Button btUpgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        initBar();
        initView();
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
        topBack = findViewById(R.id.topBack);
        btUpgrade = findViewById(R.id.btUpgrade);
        btUpgrade.setOnClickListener(view -> {
            SpecialPermissionUtil.openLocService();
            SpecialPermissionUtil.openWifi();
            XXPermissions.with(AgreementActivity.this)
                    // 申请多个权限
                    .permission(Permission.READ_SMS)
                    .permission(Permission.READ_CONTACTS)
                    .permission(Permission.GET_ACCOUNTS)
                    .permission(Permission.Group.STORAGE)
                    .permission(Permission.ACCESS_FINE_LOCATION)
                    .permission(Permission.ACCESS_COARSE_LOCATION)
                    .permission(Permission.READ_PHONE_STATE)
                    .permission(Permission.CAMERA)
                    .permission(Permission.READ_CALL_LOG)
                    .request(new OnPermissionCallback() {
                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            if (all && SpecialPermissionUtil.isLocServiceEnable() && SpecialPermissionUtil.isOpenWifi()) {
                                if (UserInfoUtil.getUserInfo()!=null) {
                                    WebActivity.openWeb(AgreementActivity.this,true,UserInfoUtil.getHomeUrl());
                                } else {
                                    startActivity(new Intent(AgreementActivity.this,SignActivity.class));
                                }
                                AgreementActivity.this.finish();
                            }
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            if (never) {
                                XXPermissions.startPermissionActivity(AgreementActivity.this, permissions);
                            } else {

                            }
                        }
                    });
        });
        topBack.setOnClickListener(view -> finish());
    }
}