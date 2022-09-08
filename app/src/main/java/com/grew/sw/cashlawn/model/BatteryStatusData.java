package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class BatteryStatusData implements Serializable {
    private int is_usb_charge;//是否 usb 充电，0：否，1：是
    private int is_ac_charge;//是否交流充电，0：否，1：是
    private String batteryPercentage;//电池百分比	0.17
    private String battery_temper;//电池状态	32
    private String battery_health;//电池寿命	2
    private int batteryStatus;//是否正在充电，UNKNOWN： 1，CHARGING：2，DISCHARGING：3

    @Override
    public String toString() {
        return "BatteryStatusData{" +
                "is_usb_charge=" + is_usb_charge +
                ", is_ac_charge=" + is_ac_charge +
                ", batteryPercentage='" + batteryPercentage + '\'' +
                ", battery_temper='" + battery_temper + '\'' +
                ", battery_health='" + battery_health + '\'' +
                ", batteryStatus=" + batteryStatus +
                '}';
    }

    public int getIs_usb_charge() {
        return is_usb_charge;
    }

    public void setIs_usb_charge(int is_usb_charge) {
        this.is_usb_charge = is_usb_charge;
    }

    public int getIs_ac_charge() {
        return is_ac_charge;
    }

    public void setIs_ac_charge(int is_ac_charge) {
        this.is_ac_charge = is_ac_charge;
    }

    public String getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(String batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public String getBattery_temper() {
        return battery_temper;
    }

    public void setBattery_temper(String battery_temper) {
        this.battery_temper = battery_temper;
    }

    public String getBattery_health() {
        return battery_health;
    }

    public void setBattery_health(String battery_health) {
        this.battery_health = battery_health;
    }

    public int getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(int batteryStatus) {
        this.batteryStatus = batteryStatus;
    }
}

