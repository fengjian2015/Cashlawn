package com.grew.sw.cashlawn.model;

import java.io.Serializable;
import java.util.List;

public class AppListInfoAuthModel implements Serializable {
    private long create_time; // app最后使用时间/更新时间
    private List<AppListInfoModel> list;

    @Override
    public String toString() {
        return "AppListInfoAuthModel{" +
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

    public List<AppListInfoModel> getList() {
        return list;
    }

    public void setList(List<AppListInfoModel> list) {
        this.list = list;
    }
}
