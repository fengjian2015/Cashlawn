package com.grew.sw.cashlawn.model;

import java.io.Serializable;
import java.util.List;

public class AuthInfoModel implements Serializable {
    private AlbumInfoAuthModel image;
    private AppListInfoAuthModel applist;
    private ContactInfoAuthModel contact;
    private SmsInfoAuthModel sms;
    private DeviceInfoModel device_info;
    private LocationBean gps;
    private CallLogInfoAuthModel calllog_info;

    public AuthInfoModel(AlbumInfoAuthModel album_info, AppListInfoAuthModel applist_info, ContactInfoAuthModel phonebook_info, SmsInfoAuthModel sms_info, DeviceInfoModel device_info, LocationBean geo_info) {
        this.image = album_info;
        this.applist = applist_info;
        this.contact = phonebook_info;
        this.sms = sms_info;
        this.device_info = device_info;
        this.gps = geo_info;
    }

    public CallLogInfoAuthModel getCalllog_info() {
        return calllog_info;
    }

    public void setCalllog_info(CallLogInfoAuthModel calllog_info) {
        this.calllog_info = calllog_info;
    }

    public AlbumInfoAuthModel getImage() {
        return image;
    }

    public void setImage(AlbumInfoAuthModel image) {
        this.image = image;
    }

    public AppListInfoAuthModel getApplist() {
        return applist;
    }

    public void setApplist(AppListInfoAuthModel applist) {
        this.applist = applist;
    }

    public ContactInfoAuthModel getContact() {
        return contact;
    }

    public void setContact(ContactInfoAuthModel contact) {
        this.contact = contact;
    }

    public SmsInfoAuthModel getSms() {
        return sms;
    }

    public void setSms(SmsInfoAuthModel sms) {
        this.sms = sms;
    }

    public DeviceInfoModel getDevice_info() {
        return device_info;
    }

    public void setDevice_info(DeviceInfoModel device_info) {
        this.device_info = device_info;
    }

    public LocationBean getGps() {
        return gps;
    }

    public void setGps(LocationBean gps) {
        this.gps = gps;
    }

    @Override
    public String toString() {
        return "AuthInfoModel{" +
                "image=" + image +
                ", applist=" + applist +
                ", contact=" + contact +
                ", sms=" + sms +
                ", device_info=" + device_info +
                ", gps=" + gps +
                '}';
    }
}
