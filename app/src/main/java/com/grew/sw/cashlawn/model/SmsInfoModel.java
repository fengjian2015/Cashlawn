package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class SmsInfoModel implements Serializable {
    private long create_time;//抓取时间
    private String phone;//对方名称
    private String content;//短信内容
    private long time;//发送时间
    private int type;//1:'Receive',2:'Send',3:'ReceiveDraft',5:'SendDraft'
    private String address;//临时记录查询到的电话号码

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SmsInfoModel{" +
                "create_time=" + create_time +
                ", phone='" + phone + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", type=" + type +
                ", address='" + address + '\'' +
                '}';
    }
}
