package com.grew.sw.cashlawn.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.grew.sw.cashlawn.model.UserInfoResponse;

import retrofit2.http.PUT;

public class UserInfoUtil {
    public static final String USER_INFO_DATA = "USER_INFO_DATA";

    private static UserInfoResponse.Data mUserInfoModel;

    public static void save(UserInfoResponse.Data userInfoModel) {
        try {
            mUserInfoModel = null;
            SparedUtils.putString(USER_INFO_DATA, new Gson().toJson(userInfoModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getUserId(){
        if (getUserInfo()!=null){
            return getUserInfo().getUserId();
        }
        return "";
    }

    public static String getSessionId(){
        if (getUserInfo()!=null){
            return getUserInfo().getSessionId();
        }
        return "";
    }

    public static String getMobileNumber(){
        if (getUserInfo()!=null){
            return getUserInfo().getMobileNumber();
        }
        return "";
    }

    public static String getHomeUrl(){
        if (getUserInfo()!=null){
            return getUserInfo().getHomeUrl();
        }
        return "";
    }

    public static UserInfoResponse.Data getUserInfo() {
        if (mUserInfoModel != null) {
            return mUserInfoModel;
        }
        try {
            String string = SparedUtils.getString(USER_INFO_DATA);
            if (TextUtils.isEmpty(string)){
                return null;
            }
            UserInfoResponse.Data userInfoModel = new Gson().fromJson(string, UserInfoResponse.Data.class);
            return userInfoModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void logout(){
        mUserInfoModel = null;
        SparedUtils.remove(USER_INFO_DATA);
    }
}
