package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class AppListInfoModel implements Serializable {
    private long last_update_time; // app最后使用时间/更新时间
    private String app_name;//应用名称
    private String version_code;//APP版本号
    private String package_name; //包名
    private long first_install_time; //安装时间 yy-mm-dd hh:mm:ss
    private String version_name;//版本名字
    private String is_system;//是否系统包      1 安装包 2系统包

    public String getIs_system() {
        return is_system;
    }

    public void setIs_system(String is_system) {
        this.is_system = is_system;
    }

    public long getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(long last_update_time) {
        this.last_update_time = last_update_time;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public long getFirst_install_time() {
        return first_install_time;
    }

    public void setFirst_install_time(long first_install_time) {
        this.first_install_time = first_install_time;
    }


    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    @Override
    public String toString() {
        return "AppListInfoModel{" +
                "last_update_time=" + last_update_time +
                ", app_name='" + app_name + '\'' +
                ", version_code='" + version_code + '\'' +
                ", package_name='" + package_name + '\'' +
                ", first_install_time=" + first_install_time +
                ", version_name='" + version_name + '\'' +
                ", is_system='" + is_system + '\'' +
                '}';
    }
}
