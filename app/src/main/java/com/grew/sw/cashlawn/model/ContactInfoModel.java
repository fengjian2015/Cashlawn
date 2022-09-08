package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class ContactInfoModel implements Serializable {
    private String group;//手机号分组	1
    private String name;//名字	Tony
    private String source;//通讯录来源	device
    private String last_used_times;//最后一次使用次数	0
    private String phone;//电话	8602583474
    private long last_update_times;//上次更新时间	1603176737569
    private int contact_times;//联系次数
    private long last_contact_time;//上次联系时间	1603176737569
    private String contact_id;//临时id

    @Override
    public String toString() {
        return "ContactInfoModel{" +
                "group='" + group + '\'' +
                ", name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", last_used_times='" + last_used_times + '\'' +
                ", phone='" + phone + '\'' +
                ", last_update_times=" + last_update_times +
                ", contact_times=" + contact_times +
                ", last_contact_time=" + last_contact_time +
                ", contact_id=" + contact_id +
                '}';
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLast_used_times() {
        return last_used_times;
    }

    public void setLast_used_times(String last_used_times) {
        this.last_used_times = last_used_times;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getLast_update_times() {
        return last_update_times;
    }

    public void setLast_update_times(long last_update_times) {
        this.last_update_times = last_update_times;
    }

    public int getContact_times() {
        return contact_times;
    }

    public void setContact_times(int contact_times) {
        this.contact_times = contact_times;
    }

    public long getLast_contact_time() {
        return last_contact_time;
    }

    public void setLast_contact_time(long last_contact_time) {
        this.last_contact_time = last_contact_time;
    }
}