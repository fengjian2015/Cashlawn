package com.grew.sw.cashlawn.view.cus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.grew.sw.cashlawn.R;
import com.grew.sw.cashlawn.model.VersionResponse;

public class UpdateDialog extends DialogFragment {
    private TextView tvContent;
    private Button btUpgrade;
    private Button btLater;
    private VersionResponse.Data data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_update_dialog, null);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        tvContent = rootView.findViewById(R.id.tvContent);
        btUpgrade = rootView.findViewById(R.id.btUpgrade);
        btLater = rootView.findViewById(R.id.btLater);
        if (data != null) {
            tvContent.setText(data.getTips());
            if (data.getMust() == 1){
                setCancelable(false);
                btLater.setVisibility(View.GONE);
            }else {
                setCancelable(true);
                btLater.setVisibility(View.VISIBLE);
            }
        }

        btUpgrade.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");//Intent.ACTION_VIEW
            Uri content_url = Uri.parse(data.url);
            intent.setData(content_url);
            startActivity(intent);
        });

        btLater.setOnClickListener(view -> {
            dismissAllowingStateLoss();
        });
    }

    public void setData(VersionResponse.Data data) {
        this.data = data;
    }
}
