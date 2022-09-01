package com.grew.sw.cashlawn.util;

import android.os.SystemClock;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final String FMT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_DATE_TIME1 = "yyyy:MM:dd HH:mm:ss";
    private static long differenceTime;

    /**
     * 获取服务器时间戳
     */
    public static long getServerTimestamp() {
        if (getDifferenceTime() == 0) {
            return System.currentTimeMillis();
        }
        return differenceTime + SystemClock.elapsedRealtime();
    }

    private static long getDifferenceTime() {
        if (differenceTime == 0) {
            Long aLong = SparedUtils.getLong(ConsUtil.KEY_SERVICE_TIME);
            differenceTime = aLong < 10  ? 0 : aLong;
        }
        return differenceTime;
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
        differenceTime = serviceTimestamp - SystemClock.elapsedRealtime();
        SparedUtils.putLong(ConsUtil.KEY_SERVICE_TIME, differenceTime);
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

    /**
     * 获取当前日期
     *
     * @param format
     * @return
     */
    public static String getCurrentDate() {
        return new SimpleDateFormat(FMT_DATE_TIME).format(getServerTimestamp());
    }
}
