package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class AlbumInfoModel implements Serializable {
    private String model;//拍摄手机机型
    private String latitude;//纬度
    private String name;//照片名称
    private String author;//拍摄者，无则获取手机品牌
    private String updateTime; //修改时间 yy-MM-dd HH:mm:ss
    private String width;//图片宽度 像素
    private String longitude;//经度
    private String addTime;//拍摄时间 yy-MM-dd HH:mm:ss
    private String height;//图片高度，像素

    @Override
    public String toString() {
        return "AlbumInfoModel{" +
                "model='" + model + '\'' +
                ", latitude='" + latitude + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", width='" + width + '\'' +
                ", longitude='" + longitude + '\'' +
                ", addTime='" + addTime + '\'' +
                ", height='" + height + '\'' +
                '}';
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
