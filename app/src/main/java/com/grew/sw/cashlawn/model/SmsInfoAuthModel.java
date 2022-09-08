package com.grew.sw.cashlawn.model;

import java.io.Serializable;
import java.util.List;

public class SmsInfoAuthModel implements Serializable {
    private long create_time;//抓取时间
    private List<SmsInfoModel> list;

    @Override
    public String toString() {
        return "SmsInfoAuthModel{" +
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

    public List<SmsInfoModel> getList() {
        return list;
    }

    public void setList(List<SmsInfoModel> list) {
        this.list = list;
    }
}
