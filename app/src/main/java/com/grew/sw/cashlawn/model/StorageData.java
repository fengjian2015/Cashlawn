package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class StorageData implements Serializable {
    private String availableDiskSize;//手机内部存储可用大小	383799296
    private String availableMemory;//手机内部存储可用大小 b
    private long elapsedRealtime;//从开机到现在的毫秒数(包括睡眠时间)
    private String isUSBDebug;//是否开启 USB 调试	false
    private String isUsingProxyPort;//是否使用代理 true false
    private String isUsingVPN;//是否使用VPN	false
    private long memorySize;//手机内部存储总大小
    private long ram_total_size;//总内存大小	1475395584
    private String totalDiskSize;//sd_card 使用了的内存大小

    @Override
    public String toString() {
        return "StorageData{" +
                "availableDiskSize='" + availableDiskSize + '\'' +
                ", availableMemory='" + availableMemory + '\'' +
                ", elapsedRealtime=" + elapsedRealtime +
                ", isUSBDebug='" + isUSBDebug + '\'' +
                ", isUsingProxyPort='" + isUsingProxyPort + '\'' +
                ", isUsingVPN='" + isUsingVPN + '\'' +
                ", memorySize=" + memorySize +
                ", ram_total_size=" + ram_total_size +
                ", totalDiskSize='" + totalDiskSize + '\'' +
                '}';
    }

    public String getAvailableDiskSize() {
        return availableDiskSize;
    }

    public void setAvailableDiskSize(String availableDiskSize) {
        this.availableDiskSize = availableDiskSize;
    }

    public String getAvailableMemory() {
        return availableMemory;
    }

    public void setAvailableMemory(String availableMemory) {
        this.availableMemory = availableMemory;
    }

    public long getElapsedRealtime() {
        return elapsedRealtime;
    }

    public void setElapsedRealtime(long elapsedRealtime) {
        this.elapsedRealtime = elapsedRealtime;
    }

    public String getIsUSBDebug() {
        return isUSBDebug;
    }

    public void setIsUSBDebug(String isUSBDebug) {
        this.isUSBDebug = isUSBDebug;
    }

    public String getIsUsingProxyPort() {
        return isUsingProxyPort;
    }

    public void setIsUsingProxyPort(String isUsingProxyPort) {
        this.isUsingProxyPort = isUsingProxyPort;
    }

    public String getIsUsingVPN() {
        return isUsingVPN;
    }

    public void setIsUsingVPN(String isUsingVPN) {
        this.isUsingVPN = isUsingVPN;
    }

    public long getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(long memorySize) {
        this.memorySize = memorySize;
    }

    public long getRam_total_size() {
        return ram_total_size;
    }

    public void setRam_total_size(long ram_total_size) {
        this.ram_total_size = ram_total_size;
    }

    public String getTotalDiskSize() {
        return totalDiskSize;
    }

    public void setTotalDiskSize(String totalDiskSize) {
        this.totalDiskSize = totalDiskSize;
    }
}
