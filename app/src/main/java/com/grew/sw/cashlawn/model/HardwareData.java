package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class HardwareData implements Serializable {
    private String device_model;//型号	SM-G532M
    private String imei;//设备号	000000000000000
    private String sys_version;//系统版本	23
    private String screenResolution;//物理尺寸	540 * 960
    private String manufacturerName;//品牌	samsung

    @Override
    public String toString() {
        return "HardwareData{" +
                "device_model='" + device_model + '\'' +
                ", imei='" + imei + '\'' +
                ", sys_version='" + sys_version + '\'' +
                ", screenResolution='" + screenResolution + '\'' +
                ", manufacturerName='" + manufacturerName + '\'' +
                '}';
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSys_version() {
        return sys_version;
    }

    public void setSys_version(String sys_version) {
        this.sys_version = sys_version;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
}
