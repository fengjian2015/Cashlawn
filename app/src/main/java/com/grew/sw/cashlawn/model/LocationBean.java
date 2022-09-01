package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class LocationBean implements Serializable {
    private String latitude;//纬度
    private String location;//当前位置名称
    private String longtitude; //经度
    private String geo_time;//获取时间 yy-MM-dd HH:mm:ss

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getGeo_time() {
        return geo_time;
    }

    public void setGeo_time(String geo_time) {
        this.geo_time = geo_time;
    }

    @Override
    public String toString() {
        return "LocationBean{" +
                "latitude='" + latitude + '\'' +
                ", location='" + location + '\'' +
                ", longtitude='" + longtitude + '\'' +
                ", geo_time='" + geo_time + '\'' +
                '}';
    }
}
