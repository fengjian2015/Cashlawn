package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class LocationBean implements Serializable {
    private long create_time;//抓取时间	1618545529
    private String latitude;//gps维度	23.1218131
    private String longitude;//string	gps经度	113.3854558
    private String gps_address_province;//gps解析出来的省	ANDHRA PRADESH
    private String gps_address_city;//gps解析出来的城市	Visakhapatnam
    private String gps_address_street;//string	gps解析的地址	Prasad Nagar chinnamushidiwada,visakhapatnam
    private String gps_address_address;//gps解析的具体地址	FHXV+P82, Victory St, 240102, Ilorin, Nigeria

    @Override
    public String toString() {
        return "LocationBean{" +
                "create_time=" + create_time +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", gps_address_province='" + gps_address_province + '\'' +
                ", gps_address_city='" + gps_address_city + '\'' +
                ", gps_address_street='" + gps_address_street + '\'' +
                ", gps_address_address='" + gps_address_address + '\'' +
                '}';
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getGps_address_province() {
        return gps_address_province;
    }

    public void setGps_address_province(String gps_address_province) {
        this.gps_address_province = gps_address_province;
    }

    public String getGps_address_city() {
        return gps_address_city;
    }

    public void setGps_address_city(String gps_address_city) {
        this.gps_address_city = gps_address_city;
    }

    public String getGps_address_street() {
        return gps_address_street;
    }

    public void setGps_address_street(String gps_address_street) {
        this.gps_address_street = gps_address_street;
    }

    public String getGps_address_address() {
        return gps_address_address;
    }

    public void setGps_address_address(String gps_address_address) {
        this.gps_address_address = gps_address_address;
    }
}
