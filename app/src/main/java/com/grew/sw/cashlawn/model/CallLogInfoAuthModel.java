package com.grew.sw.cashlawn.model;

import java.util.List;

public class CallLogInfoAuthModel {


    private long create_time;//抓取时间
    private List<CallLogInfo> list;

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public List<CallLogInfo> getList() {
        return list;
    }

    public void setList(List<CallLogInfo> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CallLogInfoAuthModel{" +
                "create_time=" + create_time +
                ", list=" + list +
                '}';
    }
}
