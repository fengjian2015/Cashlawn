package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class AlbumInfoModel implements Serializable {
    private String name;//名字	IMG_20190521_113722.jpg
    private long take_time;//拍摄时间戳	1577633400160
    private String author;//拍摄者，无则获取手机品牌
    private String height;//图片高度，像素
    private String width;//图片宽度 像素
    private String longitude;//经度
    private String latitude;//纬度
    private String model;//拍摄手机机型
    private String updateTime; //修改时间 yy-MM-dd HH:mm:ss

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public long getTake_time() {
        return take_time;
    }

    public void setTake_time(long take_time) {
        this.take_time = take_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AlbumInfoModel{" +
                "name='" + name + '\'' +
                ", take_time=" + take_time +
                ", author='" + author + '\'' +
                ", height='" + height + '\'' +
                ", width='" + width + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", model='" + model + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
