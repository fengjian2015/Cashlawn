package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class AppListInfoModel implements Serializable {
    private String lastUpdateTime; // app最后使用时间/更新时间
    private String appName;//应用名称
    private String version;//APP版本号
    private String is_system ;//是否系统包      1 安装包 2系统包
    private String packageName; //包名
    private String installationTime; //安装时间 yy-mm-dd hh:mm:ss

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIs_system() {
        return is_system;
    }

    public void setIs_system(String is_system) {
        this.is_system = is_system;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getInstallationTime() {
        return installationTime;
    }

    public void setInstallationTime(String installationTime) {
        this.installationTime = installationTime;
    }

    @Override
    public String toString() {
        return "AppListInfoModel{" +
                "lastUpdateTime='" + lastUpdateTime + '\'' +
                ", appName='" + appName + '\'' +
                ", version='" + version + '\'' +
                ", is_system='" + is_system + '\'' +
                ", packageName='" + packageName + '\'' +
                ", installationTime='" + installationTime + '\'' +
                '}';
    }
}
