package com.grew.sw.cashlawn.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.grew.sw.cashlawn.App;

import java.util.Locale;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
        int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
        int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1)/10;
        try {
            Float batteryTotal = Float.valueOf(intent.getExtras().getInt("scale"));
            Float level= Float.valueOf(intent.getExtras().getInt("level"));
            if (level != null && batteryTotal != null) {
                SparedUtils.putString(ConsUtil.KEY_BATTERY_LEVEL , String.format(Locale.getDefault(), " %.2f", level / batteryTotal));
                if (App.open_power == -1){
                    App.open_power = (int)(level / batteryTotal * 100);
                }
                App.complete_apply_power = (int)(level / batteryTotal * 100);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        if (plugged == BatteryManager.BATTERY_PLUGGED_USB){
            SparedUtils.putInt(ConsUtil.KEY_BATTERY_IS_USB,1);
        }else {
            SparedUtils.putInt(ConsUtil.KEY_BATTERY_IS_USB,0);
        }
        if (plugged == BatteryManager.BATTERY_PLUGGED_AC){
            SparedUtils.putInt(ConsUtil.KEY_BATTERY_IS_AC,1);
        }else {
            SparedUtils.putInt(ConsUtil.KEY_BATTERY_IS_AC,0);
        }

        SparedUtils.putInt(ConsUtil.KEY_BATTERY_STATUS,status);
        SparedUtils.putInt(ConsUtil.KEY_BATTERY_HEALTH,health);
        SparedUtils.putInt(ConsUtil.KEY_BATTERY_TEMPER,temperature);
    }
}
