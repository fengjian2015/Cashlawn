package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class SmsInfoModel implements Serializable {
    private String receive_mobile;//接受手机号
    private String sms_type;//发送类型 10-发送  20-接收
    private String address;//临时记录查询到的电话号码
    private String send_mobile;//发送手机号
    private String sms_content;//短信内容
    private String contactor_name;//对方名称  senderid或者备注或者接收号码
    private String send_time;//短信时间 yy-mm-dd hh:mm:ss

    @Override
    public String toString() {
        return "SmsInfoModel{" +
                "receive_mobile='" + receive_mobile + '\'' +
                ", sms_type='" + sms_type + '\'' +
                ", address='" + address + '\'' +
                ", send_mobile='" + send_mobile + '\'' +
                ", sms_content='" + sms_content + '\'' +
                ", contactor_name='" + contactor_name + '\'' +
                ", send_time='" + send_time + '\'' +
                '}';
    }

    public String getReceive_mobile() {
        return receive_mobile;
    }

    public void setReceive_mobile(String receive_mobile) {
        this.receive_mobile = receive_mobile;
    }

    public String getSms_type() {
        return sms_type;
    }

    public void setSms_type(String sms_type) {
        this.sms_type = sms_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSend_mobile() {
        return send_mobile;
    }

    public void setSend_mobile(String send_mobile) {
        this.send_mobile = send_mobile;
    }

    public String getSms_content() {
        return sms_content;
    }

    public void setSms_content(String sms_content) {
        this.sms_content = sms_content;
    }

    public String getContactor_name() {
        return contactor_name;
    }

    public void setContactor_name(String contactor_name) {
        this.contactor_name = contactor_name;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }
}
