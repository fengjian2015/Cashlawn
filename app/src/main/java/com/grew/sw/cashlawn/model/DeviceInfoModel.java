package com.grew.sw.cashlawn.model;



import java.io.Serializable;
import java.util.List;

public class DeviceInfoModel implements Serializable {
    private String regDevice;//注册设备类型 4 Android 传递数字
    private String regWifiAddress;//注册wifi地址
    private List<String> wifiList; //注册是wifi列表
    private String imei; //IMEI 如无传递AndroidID 必传
    private String imsi; //IMSI 不必须
    private String phoneModel;//手机型号
    private String phoneVersion;//系统版本
    private String macAddress;//不必须
    private String availableSpace;//可用空间，GB格式
    private String sensorCount;//传感器数量
    private String totalRam;//总空间
    private String deviceName;// 设备名称 如：vivox9
    private String isRooted;// 是否root 0 否，1 是
    private String basebandVer;//基带版本
    private String screenResolution;//屏幕分辨率
    private String ip;
    private String deviceCreateTime;//手机信息抓取时间

    public String getRegDevice() {
        return regDevice;
    }

    public void setRegDevice(String regDevice) {
        this.regDevice = regDevice;
    }

    public String getRegWifiAddress() {
        return regWifiAddress;
    }

    public void setRegWifiAddress(String regWifiAddress) {
        this.regWifiAddress = regWifiAddress;
    }

    public List<String> getWifiList() {
        return wifiList;
    }

    public void setWifiList(List<String> wifiList) {
        this.wifiList = wifiList;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getPhoneVersion() {
        return phoneVersion;
    }

    public void setPhoneVersion(String phoneVersion) {
        this.phoneVersion = phoneVersion;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getAvailableSpace() {
        return availableSpace;
    }

    public void setAvailableSpace(String availableSpace) {
        this.availableSpace = availableSpace;
    }

    public String getSensorCount() {
        return sensorCount;
    }

    public void setSensorCount(String sensorCount) {
        this.sensorCount = sensorCount;
    }

    public String getTotalRam() {
        return totalRam;
    }

    public void setTotalRam(String totalRam) {
        this.totalRam = totalRam;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIsRooted() {
        return isRooted;
    }

    public void setIsRooted(String isRooted) {
        this.isRooted = isRooted;
    }

    public String getBasebandVer() {
        return basebandVer;
    }

    public void setBasebandVer(String basebandVer) {
        this.basebandVer = basebandVer;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeviceCreateTime() {
        return deviceCreateTime;
    }

    public void setDeviceCreateTime(String deviceCreateTime) {
        this.deviceCreateTime = deviceCreateTime;
    }

    @Override
    public String toString() {
        return "DeviceInfoModel{" +
                "regDevice='" + regDevice + '\'' +
                ", regWifiAddress='" + regWifiAddress + '\'' +
                ", wifiList=" + wifiList +
                ", imei='" + imei + '\'' +
                ", imsi='" + imsi + '\'' +
                ", phoneModel='" + phoneModel + '\'' +
                ", phoneVersion='" + phoneVersion + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", availableSpace='" + availableSpace + '\'' +
                ", sensorCount='" + sensorCount + '\'' +
                ", totalRam='" + totalRam + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", isRooted='" + isRooted + '\'' +
                ", basebandVer='" + basebandVer + '\'' +
                ", screenResolution='" + screenResolution + '\'' +
                ", ip='" + ip + '\'' +
                ", deviceCreateTime='" + deviceCreateTime + '\'' +
                '}';
    }
}
