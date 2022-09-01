package com.grew.sw.cashlawn.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.grew.sw.cashlawn.model.VersionResponse;
import com.grew.sw.cashlawn.network.NetCallback;
import com.grew.sw.cashlawn.network.NetClient;
import com.grew.sw.cashlawn.network.NetErrorModel;
import com.grew.sw.cashlawn.network.NetUtil;
import com.grew.sw.cashlawn.view.cus.UpdateDialog;

public class UpdateUtil {
    public static void checkUpdate(FragmentActivity activity) {
        NetClient.getNewService()
                .requestVersion()
                .compose(NetUtil.applySchedulers())
                .subscribe(new NetCallback<VersionResponse>() {
                    @Override
                    public void businessFail(NetErrorModel netErrorModel) {

                    }

                    @Override
                    public void businessSuccess(VersionResponse d) {
                        if (d != null && d.getCode() == 200 && d.getData() != null) {
                            VersionResponse.Data data = d.getData();
                            Integer integer = ComUtil.stringToInt(data.getCode());
                            if (integer > ComUtil.getVersionCode() && activity != null && !activity.isFinishing()) {
                                UpdateDialog updateDialog = new UpdateDialog();
                                updateDialog.setData(data);
                                updateDialog.show(activity.getSupportFragmentManager(), "up");
                            }
                        }
                    }
                });
    }
}
