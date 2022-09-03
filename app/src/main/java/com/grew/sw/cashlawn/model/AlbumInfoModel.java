package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class AlbumInfoModel implements Serializable {
    private long create_time;//抓取时间	1618545529
    private String name;//名字	IMG_20190521_113722.jpg
    private long take_time;//拍摄时间戳	1577633400160

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
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
                "create_time=" + create_time +
                ", name='" + name + '\'' +
                ", take_time=" + take_time +
                '}';
    }
}
