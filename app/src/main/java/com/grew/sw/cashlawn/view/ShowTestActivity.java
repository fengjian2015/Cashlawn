package com.grew.sw.cashlawn.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.grew.sw.cashlawn.R;
import com.grew.sw.cashlawn.util.ToastUtils;

public class ShowTestActivity extends AppCompatActivity {


    private String content = "";

    public static void start(Context context, String content){
        if (context == null){
            return;
        }
        Intent intent = new Intent(context, ShowTestActivity.class);
        intent.putExtra("content", content);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_test);


        Intent intent = getIntent();
        if (intent != null){
            content = intent.getStringExtra("content");
        }


        TextView textView = findViewById(R.id.tv_content);

        textView.setText(content);


        findViewById(R.id.btn_copy).setOnClickListener(v -> {
            copy(getBaseContext(), content);
            ToastUtils.showShort("复制成功");
        });

    }


    public static void copy(Context context, String text) {
        if (context == null || TextUtils.isEmpty(text)) {
            return;
        }
        //获取剪贴板管理器
        ClipboardManager manager = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager == null) {
            return;
        }
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("storm", text);
        // 将ClipData内容放到系统剪贴板里。
        manager.setPrimaryClip(mClipData);
    }
}
