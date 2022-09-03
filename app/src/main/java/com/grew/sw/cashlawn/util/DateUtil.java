package com.grew.sw.cashlawn.util;

import android.os.SystemClock;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final String FMT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取服务器时间戳
     */
    public static long getServerTimestamp() {
        Long aLong = SparedUtils.getLong(ConsUtil.KEY_SERVICE_TIME);
        Long elapsedRealtime = SparedUtils.getLong(ConsUtil.KEY_DIFFERENCE_TIME);
        if (aLong < 10) {
            return System.currentTimeMillis();
        }else {
            return (SystemClock.elapsedRealtime() - elapsedRealtime) + aLong;
        }
    }

    public static void initTime(String s) {
        if (TextUtils.isEmpty(s))return;
        long serviceTimestamp = 0;
        try {
            serviceTimestamp = Long.parseLong(s);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (serviceTimestamp == 0){
            return;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        SparedUtils.putLong(ConsUtil.KEY_SERVICE_TIME, serviceTimestamp);
        SparedUtils.putLong(ConsUtil.KEY_DIFFERENCE_TIME, elapsedRealtime);
    }

    /**
     * 將时间戳转日期
     *
     * @param format
     * @return
     */
    public static String getTimeFromLongYMDHMS(long time) {
        try {
            return new SimpleDateFormat(FMT_DATE_TIME).format(time);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
