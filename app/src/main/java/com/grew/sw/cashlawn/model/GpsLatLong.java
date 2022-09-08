package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class GpsLatLong implements Serializable {
    private String latitude;//gps维度	23.1218131
    private String longitude;//string	gps经度	113.3854558

    @Override
    public String toString() {
        return "GpsLatLong{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
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
}
