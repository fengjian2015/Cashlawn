package com.grew.sw.cashlawn.util;

import android.os.SystemClock;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 * @author Nevio
 * on 2015/2/28.
 */
public class TimeUtil {

    private static String[] dayNameArr = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private static final String TIME_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT_YMD = "yyyy-MM-dd";

    private static long appLaunchSystemTimestamp;
    private static long appLaunchServiceTimestamp;

    public static void initTime(long serviceTimestamp) {
        appLaunchSystemTimestamp = SystemClock.elapsedRealtime();
        appLaunchServiceTimestamp = serviceTimestamp;
    }

    /**
     * 得到当前时间
     * @param dateFormat 时间格式
     * @return 转换后的时间格式
     */
    @NonNull
    public static String getCurrentTime(String dateFormat) {
        if (TextUtils.isEmpty(dateFormat)) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(dateFormat);
        if (simpleDateFormat == null) {
            return "";
        }
        return simpleDateFormat.format(new Date());
    }

    @NonNull
    public static String getCurrentTime() {
        return getCurrentTime(TIME_FORMAT_DEFAULT);
    }



    /**
     * 获取毫秒时间戳
     */
    public static long getMilliTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取微秒时间戳
     */
    public static long getMicroTimestamp() {
        long currentTime = System.currentTimeMillis() * 1000; // 微秒
        long nanoTime = System.nanoTime(); // 纳秒
        return currentTime + (nanoTime - nanoTime / 1000000 * 1000000) / 1000;
    }

    /**
     * 获取服务器时间戳
     */
    public static long getServerTimestamp() {
        long currentSystemTimestamp = SystemClock.elapsedRealtime();
        return appLaunchServiceTimestamp + currentSystemTimestamp - appLaunchSystemTimestamp;
    }

    /**
     * 时间戳转字符串日期
     */
    @NonNull
    public static String timestampToStr(long timeStamp, String timeFormat) {
        if (TextUtils.isEmpty(timeFormat)) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(timeFormat);
        if (simpleDateFormat == null) {
            return "";
        }
        return simpleDateFormat.format(new Date(timeStamp));
    }

    @NonNull
    public static String timestampToStr(long timeStamp) {
        return timestampToStr(timeStamp, TIME_FORMAT_DEFAULT);
    }

    @Nullable
    public static Date timestampToDate(long timestamp) {
        return strToDate(timestampToStr(timestamp));
    }

    /**
     * 时间戳格式化
     */
    public static long timestampFormat(long timestamp, String timeFormat) {
        return strToTimestamp(timestampToStr(timestamp, timeFormat), timeFormat);
    }

    /**
     * 字符串日期转时间戳
     */
    public static long strToTimestamp(String timeStr) {
        return dateToTimestamp(strToDate(timeStr, TIME_FORMAT_DEFAULT));
    }

    public static long strToTimestamp(String timeStr, String timeFormat) {
        if (TextUtils.isEmpty(timeStr) || TextUtils.isEmpty(timeFormat)) {
            return 0L;
        }
        return dateToTimestamp(strToDate(timeStr, timeFormat));
    }

    /**
     * 字符串日期转Date
     * @param dateStr    字符串日期
     * @param dateFormat 日期格式
     */
    @Nullable
    public static Date strToDate(String dateStr, String dateFormat) {
        if (TextUtils.isEmpty(dateStr) || TextUtils.isEmpty(dateFormat)) {
            return null;
        }
        try {
            SimpleDateFormat simpleDateFormat = getSimpleDateFormat(dateFormat);
            if (simpleDateFormat == null) {
                return null;
            }
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    @Nullable
    public static Date strToDate(String dateStr) {
        return strToDate(dateStr, TIME_FORMAT_DEFAULT);
    }

    /**
     * yyyy-MM-dd HH:mm:ss字符串日期转新格式字符串日期
     */
    @NonNull
    public static String strToStr(String dateStr, String newDateFormat) {
        if (TextUtils.isEmpty(dateStr) || TextUtils.isEmpty(newDateFormat)) {
            return "";
        }
        Date date = strToDate(dateStr);
        if (date != null) {
            return dateToStr(date, newDateFormat);
        } else {
            return "";
        }
    }

    @NonNull
    public static String strToStr(String dateStr, String oldDateFormat, String newDateFormat) {
        if (TextUtils.isEmpty(dateStr) || TextUtils.isEmpty(oldDateFormat)
            || TextUtils.isEmpty(newDateFormat)) {
            return "";
        }
        Date date = strToDate(dateStr, oldDateFormat);
        if (date != null) {
            return dateToStr(date, newDateFormat);
        } else {
            return "";
        }
    }

    /**
     * date转时间戳
     */
    public static long dateToTimestamp(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
            return calendar.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Date转字符串日期
     */
    @NonNull
    public static String dateToStr(Date date, String dateFormat) {
        if (date == null || TextUtils.isEmpty(dateFormat)) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(dateFormat);
        if (simpleDateFormat == null) {
            return "";
        }
        return simpleDateFormat.format(date);
    }

    @NonNull
    public static String dateToStr(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(TIME_FORMAT_DEFAULT);
        if (simpleDateFormat == null) {
            return "";
        }
        return simpleDateFormat.format(date);
    }

    /**
     * 日期加减
     * @param timeFormat 当前传入时间格式及输出格式
     * @param num        待加减数量，负为减
     */
    @NonNull
    public static String dateAdd(String timeStr, String timeFormat, int num) {
        if (TextUtils.isEmpty(timeStr) || TextUtils.isEmpty(timeFormat)) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(strToDate(timeStr, timeFormat));
            calendar.add(Calendar.DATE, num);
            return dateToStr(calendar.getTime(), timeFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 月份加减
     * @param timeFormat 当前传入时间格式及输出格式
     * @param num        待加减数量，负为减
     */
    @NonNull
    public static String monthAdd(String timeStr, String timeFormat, int num) {
        if (TextUtils.isEmpty(timeStr) || TextUtils.isEmpty(timeFormat)) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(strToDate(timeStr, timeFormat));
            calendar.add(Calendar.MONTH, num);
            return dateToStr(calendar.getTime(), timeFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定时间间隔分钟后的时间
     * @param date 指定的时间
     * @param min  间隔分钟数
     * @return 间隔分钟数后的时间
     */
    @Nullable
    public static Date addMinutes(Date date, int min) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, min);
        return calendar.getTime();
    }

    /**
     * 获取星期几
     */
    @NonNull
    public static String getDayOfWeek(String timeStr, String dateFormat) {
        if (TextUtils.isEmpty(timeStr) || TextUtils.isEmpty(dateFormat)) {
            return "";
        }
        String week = "";
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(strToDate(timeStr, dateFormat));
            int weekday = calendar.get(Calendar.DAY_OF_WEEK);
            switch (weekday) {
                case 1:
                    week = "周日";
                    break;
                case 2:
                    week = "周一";
                    break;
                case 3:
                    week = "周二";
                    break;
                case 4:
                    week = "周三";
                    break;
                case 5:
                    week = "周四";
                    break;
                case 6:
                    week = "周五";
                    break;
                case 7:
                    week = "周六";
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return week;
    }

    @NonNull
    public static String getDayOfWeek(String timeStr) {
        return getDayOfWeek(timeStr, "yyyy-MM-dd");
    }

    /**
     * 两个时间点的间隔时长
     * 得到的差值是毫秒级别
     * @param before 开始时间
     * @param after  结束时间
     * @return 两个时间点的间隔时长
     */
    public static long calTimestampDiff(Date before, Date after) {
        if (before == null || after == null) {
            return 0;
        }
        long dif = 0;
        if (after.getTime() >= before.getTime()) {
            dif = after.getTime() - before.getTime();
        } else if (after.getTime() < before.getTime()) {
            dif = after.getTime() + 86400000 - before.getTime();
        }
        return Math.abs(dif);
    }

    public static long calDaysDiff(Date before, Date after) {
        return calTimestampDiff(before, after) / (1000 * 60 * 60 * 24);
    }

    public static long calDaysDiff(long beforeTimestamp, long afterTimestamp) {
        return calDaysDiff(timestampToDate(beforeTimestamp), timestampToDate(afterTimestamp));
    }

    @NonNull
    public static String formatTimeStr(long durationSecond) {
        int minute = (int) durationSecond / 60;
        int second = (int) durationSecond - (minute * 60);
        String minuteStr;
        String secondStr;
        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = String.valueOf(minute);
        }
        if (second < 10) {
            secondStr = "0" + second;
        } else {
            secondStr = String.valueOf(second);
        }
        return minuteStr + ":" + secondStr;
    }

    public static boolean isSameDay(long firstTimestamp, long secondTimestamp) {
        Calendar firstCalendar = Calendar.getInstance();
        Calendar secondCalendar = Calendar.getInstance();
        firstCalendar.setTimeInMillis(firstTimestamp);
        secondCalendar.setTimeInMillis(secondTimestamp);
        return firstCalendar.get(Calendar.DAY_OF_YEAR) - secondCalendar.get(Calendar.DAY_OF_YEAR) == 0;
    }

    @NonNull
    public static String getFormatDate(long inputTimestamp) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar inputCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(getServerTimestamp());
        inputCalendar.setTimeInMillis(inputTimestamp);
        int currentDay = currentCalendar.get(Calendar.DAY_OF_YEAR);
        int inputDay = inputCalendar.get(Calendar.DAY_OF_YEAR);
        if (currentDay - inputDay == 0) {
            //当天
            return "今天";
        } else if (currentDay - inputDay == 1) {
            //昨天
            return "昨天";
        } else {
            return timestampToStr(inputTimestamp, "M月d日");
        }
    }

    //格式化時間格式仿微信顯示
    @NonNull
    public static String getWeChatTime(long currentShowTime) {
        String formatResult = "";
        try {
            formatResult = formatWeChatTime(currentShowTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatResult;
    }

    @NonNull
    private static String formatWeChatTime(long timestamp) {
        String result;
        Calendar todayCalendar = Calendar.getInstance();
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTimeInMillis(timestamp);
        String timeFormat;
        String yearTimeFormat;
        String am_pm;
        int hour = otherCalendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 6) {
            am_pm = "凌晨";
        } else if (hour < 12) {
            am_pm = "早上";
        } else if (hour == 12) {
            am_pm = "中午";
        } else if (hour < 18) {
            am_pm = "下午";
        } else {
            am_pm = "晚上";
        }
        timeFormat = "M月d日 " + am_pm + "HH:mm";
        yearTimeFormat = "yyyy年M月d日 " + am_pm + "HH:mm";
        boolean yearTemp = todayCalendar.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR);
        if (yearTemp) {
            int todayMonth = todayCalendar.get(Calendar.MONTH);
            int otherMonth = otherCalendar.get(Calendar.MONTH);
            if (todayMonth == otherMonth) {//表示是同一个月
                int temp = todayCalendar.get(Calendar.DATE) - otherCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        result = getHourAndMin(timestamp);
                        break;
                    case 1:
                        result = "昨天 " + getHourAndMin(timestamp);
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        int dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH);
                        int todayOfMonth = todayCalendar.get(Calendar.WEEK_OF_MONTH);
                        if (dayOfMonth == todayOfMonth) {//表示是同一周
                            int dayOfWeek = otherCalendar.get(Calendar.DAY_OF_WEEK);
                            if (dayOfWeek != 1) {//判断当前是不是星期日   如想显示为：周日 12:09 可去掉此判断
                                result = dayNameArr[otherCalendar.get(Calendar.DAY_OF_WEEK) - 1] + getHourAndMin(timestamp);
                            } else {
                                result = getTime(timestamp, timeFormat);
                            }
                        } else {
                            result = getTime(timestamp, timeFormat);
                        }
                        break;
                    default:
                        result = getTime(timestamp, timeFormat);
                        break;
                }
            } else if (todayCalendar.get(Calendar.DAY_OF_YEAR) - otherCalendar.get(Calendar.DAY_OF_YEAR) == 1) {
                result = "昨天 " + getHourAndMin(timestamp);
            } else {
                result = getTime(timestamp, timeFormat);
            }
        } else {
            result = getYearTime(timestamp, yearTimeFormat);
        }
        return result;
    }

    @NonNull
    public static String getConversation(long timestamp) {
        String result;
        Calendar todayCalendar = Calendar.getInstance();
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTimeInMillis(timestamp);
        String timeFormat;
        String yearTimeFormat;
        timeFormat = "yy/M/d";
        yearTimeFormat = "yy/M/d";
        boolean yearTemp = todayCalendar.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR);
        if (yearTemp) {
            int todayMonth = todayCalendar.get(Calendar.MONTH);
            int otherMonth = otherCalendar.get(Calendar.MONTH);
            if (todayMonth == otherMonth) {//表示是同一个月
                int temp = todayCalendar.get(Calendar.DATE) - otherCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        result = getHourAndMin(timestamp);
                        break;
                    case 1:
                        result = "昨天";
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        int dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH);
                        int todayOfMonth = todayCalendar.get(Calendar.WEEK_OF_MONTH);
                        if (dayOfMonth == todayOfMonth) {//表示是同一周
                            int dayOfWeek = otherCalendar.get(Calendar.DAY_OF_WEEK);
                            if (dayOfWeek != 1) {//判断当前是不是星期日   如想显示为：周日 12:09 可去掉此判断
                                result = dayNameArr[otherCalendar.get(Calendar.DAY_OF_WEEK) - 1];
                            } else {
                                result = getTime(timestamp, timeFormat);
                            }
                        } else {
                            result = getTime(timestamp, timeFormat);
                        }
                        break;
                    default:
                        result = getTime(timestamp, timeFormat);
                        break;
                }
            } else if (todayCalendar.get(Calendar.DAY_OF_YEAR) - otherCalendar.get(Calendar.DAY_OF_YEAR) == 1) {
                result = "昨天 ";
            } else {
                result = getTime(timestamp, timeFormat);
            }
        } else {
            result = getYearTime(timestamp, yearTimeFormat);
        }
        return result;
    }

    /**
     * 当天的显示时间格式
     */
    @NonNull
    public static String getHourAndMin(long time) {
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat("HH:mm");
        if (simpleDateFormat == null) {
            return "";
        }
        return simpleDateFormat.format(new Date(time));
    }

    /**
     * 不同一周的显示时间格式
     */
    @NonNull
    public static String getTime(long time, @NonNull String timeFormat) {
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(timeFormat);
        if (simpleDateFormat == null) {
            return "";
        }
        return simpleDateFormat.format(new Date(time));
    }

    /**
     * 不同年的显示时间格式
     */
    @NonNull
    public static String getYearTime(long time, @NonNull String yearTimeFormat) {
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(yearTimeFormat);
        if (simpleDateFormat == null) {
            return "";
        }
        return simpleDateFormat.format(new Date(time));
    }

    @Nullable
    public static SimpleDateFormat getSimpleDateFormat(String timeFormat) {
        if (TextUtils.isEmpty(timeFormat)) {
            return null;
        }
        return new SimpleDateFormat(timeFormat, Locale.getDefault());
    }

}
