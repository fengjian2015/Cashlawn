package com.grew.sw.cashlawn.util;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

import com.grew.sw.cashlawn.App;

public class SpecialPermissionUtil {
    public static boolean isLocServiceEnable() {
        LocationManager locationManager = (LocationManager) App.get().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return true;
        }
        return false;
    }

    public static void openLocService(){
        if (!isLocServiceEnable()) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.get().startActivity(intent);
        }
    }

    /**
     * 开启wifi
     */
    public static void openWifi() {
        WifiManager wifiManager = (WifiManager) App.get().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Intent i =new Intent(Settings.ACTION_WIFI_SETTINGS);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                App.get().startActivity(i);
            } else {
                wifiManager.setWifiEnabled(true);
            }
        }
    }

    public static Boolean isOpenWifi(){
        WifiManager wifiManager = (WifiManager) App.get().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }
}
