package com.grew.sw.cashlawn.model;

import java.io.Serializable;
import java.util.List;

public class ContactInfoAuthModel implements Serializable {
    private long create_time;//抓取时间
    private List<ContactInfoModel> list;

    @Override
    public String toString() {
        return "ContactInfoAuthModel{" +
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

    public List<ContactInfoModel> getList() {
        return list;
    }

    public void setList(List<ContactInfoModel> list) {
        this.list = list;
    }
}