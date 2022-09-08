package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class DeviceInfoData implements Serializable {
    private String isRooted;//是否root	false

    @Override
    public String toString() {
        return "DeviceInfoData{" +
                "isRooted='" + isRooted + '\'' +
                '}';
    }

    public String getIsRooted() {
        return isRooted;
    }

    public void setIsRooted(String isRooted) {
        this.isRooted = isRooted;
    }
}

