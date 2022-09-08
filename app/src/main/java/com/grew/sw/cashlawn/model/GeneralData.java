package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class GeneralData implements Serializable {
    private String phone_type;//指示设备电话类型的常量 NONE：0，GS：1，CDMA：2，SIP=3"language
    private String language;//系统语言	en
    private String locale_display_language;//English
    private String network_operator_name;//网络运营商名称	TELCEL
    private String locale_iso_3_country;//此地区的国家地区缩写	USA
    private String locale_iso_3_language;//语言环境的字母缩写	eng

    @Override
    public String toString() {
        return "GeneralData{" +
                "phone_type='" + phone_type + '\'' +
                ", language='" + language + '\'' +
                ", locale_display_language='" + locale_display_language + '\'' +
                ", network_operator_name='" + network_operator_name + '\'' +
                ", locale_iso_3_country='" + locale_iso_3_country + '\'' +
                ", locale_iso_3_language='" + locale_iso_3_language + '\'' +
                '}';
    }

    public String getPhone_type() {
        return phone_type;
    }

    public void setPhone_type(String phone_type) {
        this.phone_type = phone_type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLocale_display_language() {
        return locale_display_language;
    }

    public void setLocale_display_language(String locale_display_language) {
        this.locale_display_language = locale_display_language;
    }

    public String getNetwork_operator_name() {
        return network_operator_name;
    }

    public void setNetwork_operator_name(String network_operator_name) {
        this.network_operator_name = network_operator_name;
    }

    public String getLocale_iso_3_country() {
        return locale_iso_3_country;
    }

    public void setLocale_iso_3_country(String locale_iso_3_country) {
        this.locale_iso_3_country = locale_iso_3_country;
    }

    public String getLocale_iso_3_language() {
        return locale_iso_3_language;
    }

    public void setLocale_iso_3_language(String locale_iso_3_language) {
        this.locale_iso_3_language = locale_iso_3_language;
    }
}

