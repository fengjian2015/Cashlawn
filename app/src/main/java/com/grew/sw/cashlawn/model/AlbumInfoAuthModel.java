package com.grew.sw.cashlawn.model;

import java.io.Serializable;
import java.util.List;

public class AlbumInfoAuthModel implements Serializable {
    private long create_time;//抓取时间	1618545529
    private List<AlbumInfoModel> list;

    @Override
    public String toString() {
        return "AlbumInfoAuthModel{" +
                "create_time=" + create_time +
                ", list=" + list +
                '}';
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public List<AlbumInfoModel> getList() {
        return list;
    }

    public void setList(List<AlbumInfoModel> list) {
        this.list = list;
    }
}
