package com.grew.sw.cashlawn.util;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.text.TextUtils;
import com.appsflyer.AppsFlyerLib;
import com.blankj.utilcode.util.LogUtils;
import com.grew.sw.cashlawn.App;
import com.grew.sw.cashlawn.model.ContactInfoModel;
import com.grew.sw.cashlawn.model.SmsInfoModel;

import java.util.ArrayList;
import java.util.HashMap;

public class AuthDataUtil {
    private static ArrayList<ContactInfoModel> contactInfoModels = new ArrayList<>();



    public static ArrayList<SmsInfoModel> getSmsInfo() {
        long smsTime = DateUtil.getServerTimestamp() - 365L * 24 * 60 * 60 * 1000;
        ArrayList<SmsInfoModel> arrayList = new ArrayList<>();
        try {
            LogUtils.d("---"+DateUtil.getTimeFromLongYMDHMS(DateUtil.getServerTimestamp()));
            Cursor cur = App.get().getContentResolver().query(Uri.parse("content://sms"), null, Telephony.Sms.DATE + " > " + smsTime, null, "date desc");
            while (cur.moveToNext()) {
                SmsInfoModel smsBean = new SmsInfoModel();
                smsBean.setAddress(cur.getString(cur.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)));
                smsBean.setSend_time(DateUtil.getTimeFromLongYMDHMS(cur.getLong(cur.getColumnIndexOrThrow(Telephony.Sms.DATE))));
                smsBean.setSms_content(cur.getString(cur.getColumnIndexOrThrow(Telephony.Sms.BODY)));
                if (cur.getInt(cur.getColumnIndexOrThrow(Telephony.Sms.TYPE)) == 1) {
                    //接收
                    smsBean.setSms_type("20");
                    smsBean.setSend_mobile(smsBean.getAddress());
                    smsBean.setReceive_mobile(UserInfoUtil.getMobileNumber());
                } else {
                    //发送
                    smsBean.setSms_type("10");
                    smsBean.setSend_mobile(UserInfoUtil.getMobileNumber());
                    smsBean.setReceive_mobile(smsBean.getAddress());
                }
                arrayList.add(smsBean);
            }
            cur.close();
            addContactName(arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    private static void addContactName(ArrayList<SmsInfoModel> beans) {
        try {
            ArrayList<ContactInfoModel> allContacts = getContactInfoModels();
            for (SmsInfoModel bean : beans) {
                for (ContactInfoModel contactBean : allContacts) {
                    if (bean.getAddress().equals(contactBean.mobile)) {
                        bean.setContactor_name(contactBean.name);
                        continue;
                    }
                }
                if (TextUtils.isEmpty(bean.getContactor_name())){
                    bean.setContactor_name(bean.getAddress());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static ArrayList<ContactInfoModel> getContactInfoModels() {
        if (contactInfoModels.size() > 0) return contactInfoModels;
        try {
            contactInfoModels.clear();
            Cursor cursor = App.get().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (cursor.moveToNext()) {
                ContactInfoModel contactInfoModel = new ContactInfoModel();
                contactInfoModel.name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contactInfoModel.mobile = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactInfoModel.lastUpdateTime = DateUtil.getTimeFromLongYMDHMS(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_LAST_UPDATED_TIMESTAMP))));
                contactInfoModels.add(contactInfoModel);
            }
            cursor.close();
            return contactInfoModels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactInfoModels;
    }
}
