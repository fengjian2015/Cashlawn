package com.grew.sw.cashlawn;

import static com.grew.sw.cashlawn.util.ConsUtil.APPS_FLYER_KEY;
import static com.grew.sw.cashlawn.util.ConsUtil.BASE_URL;

import android.app.Application;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.grew.sw.cashlawn.network.NetClient;
import com.grew.sw.cashlawn.util.ConsUtil;
import com.grew.sw.cashlawn.util.IActivityManager;
import com.grew.sw.cashlawn.util.LogUtils;
import com.grew.sw.cashlawn.util.SparedUtils;

import java.util.Map;

public class App extends Application {
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        IActivityManager.init(this);
        LogUtils.setLogEnable(BuildConfig.DEBUG);
        AppsFlyerLib.getInstance().setDebugLog(BuildConfig.DEBUG);
        AppsFlyerLib.getInstance().init(APPS_FLYER_KEY, new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                LogUtils.d("AppsFlyerLib Success: " + map.toString());
                for (String attrName : map.keySet()) {
                    if (!"af_status".equals(attrName)) return;
                    if ("Organic".equals((String) map.get(attrName))) {
                        SparedUtils.putString(ConsUtil.KEY_AF_CHANNEL, (String) map.get(attrName));
                    } else if ("Non-organic".equals((String) map.get(attrName))) {
                        String media_source = map.get("media_source").toString();
                        SparedUtils.putString(ConsUtil.KEY_AF_CHANNEL, media_source);
                    }
                }
            }

            @Override
            public void onConversionDataFail(String s) {}

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {}

            @Override
            public void onAttributionFailure(String s) {}
        }, this);
        AppsFlyerLib.getInstance().start(this, APPS_FLYER_KEY, null);
    }

    public static Application get() {
        return application;
    }

}
