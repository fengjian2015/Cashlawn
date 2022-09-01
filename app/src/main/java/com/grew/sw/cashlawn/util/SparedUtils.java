package com.grew.sw.cashlawn.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.grew.sw.cashlawn.App;

public class SparedUtils {

    private static SharedPreferences mSharedPreferences;
    private static final String FILLNAME = "cashlawn"; // 文件名称

    private static SharedPreferences getInstance(){
        if (mSharedPreferences ==null){
            mSharedPreferences = App.get().getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public static void putBoolean(String key,Boolean value){
        getInstance().edit().putBoolean(key,value).apply();
    }

    public static Boolean getBoolean(String key,Boolean defValue){
        return getInstance().getBoolean(key,defValue);
    }

    public static void putString(String key,String value){
        getInstance().edit().putString(key,value).apply();
    }

    public static String getString(String key,String defValue){
        return getInstance().getString(key,defValue);
    }

    public static String getString(String key){
        return getInstance().getString(key,"");
    }

    public static void putInt(String key,Integer value){
        getInstance().edit().putInt(key,value).apply();
    }

    public static Integer getInt(String key,Integer defValue){
        return getInstance().getInt(key,defValue);
    }

    public static Integer getInt(String key){
        return getInstance().getInt(key,0);
    }

    public static void putLong(String key,Long value){
        getInstance().edit().putLong(key,value).apply();
    }

    public static Long getLong(String key,Long defValue){
        return getInstance().getLong(key,defValue);
    }

    public static Long getLong(String key){
        return getInstance().getLong(key,0);
    }

    public static void remove(String key){
        getInstance().edit().remove(key).apply();
    }

    public static void clear(){
        getInstance().edit().clear().apply();
    }

}
