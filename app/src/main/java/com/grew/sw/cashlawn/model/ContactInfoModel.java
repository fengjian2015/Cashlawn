package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class ContactInfoModel implements Serializable {
    public String lastUpdateTime; //yy-MM-dd HH:mm:ss
    public String name;
    public String mobile;

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "ContactInfoModel{" +
                "lastUpdateTime='" + lastUpdateTime + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}