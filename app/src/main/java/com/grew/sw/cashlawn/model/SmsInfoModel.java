package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class SmsInfoModel implements Serializable {

    private String phone;//对方名称
    private String content;//短信内容
    private long time;//发送时间
    private int type;//1:'Receive',2:'Send',3:'ReceiveDraft',5:'SendDraft'
    private String address;//临时记录查询到的电话号码
    private String contactor_name;//对方名称  senderid或者备注或者接收号码

    public String getContactor_name() {
        return contactor_name;
    }

    public void setContactor_name(String contactor_name) {
        this.contactor_name = contactor_name;
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
                "phone='" + phone + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", type=" + type +
                ", address='" + address + '\'' +
                ", contactor_name='" + contactor_name + '\'' +
                '}';
    }
}
