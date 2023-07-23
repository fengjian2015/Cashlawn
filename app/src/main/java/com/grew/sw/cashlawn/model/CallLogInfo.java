package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class CallLogInfo implements Serializable {

    private String callDuration;///通话时长"162",
    private String callName;//通话姓名
    private String callNumber;//通话号码
    private String callTime;//拨打时间"2023-06-21 13:01:39",
    private int callType;//通话类型：1、呼出，2、呼入，3、未接通，4、挂断



    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    @Override
    public String toString() {
        return "CallLogInfo{" +
                "callDuration='" + callDuration + '\'' +
                ", callName='" + callName + '\'' +
                ", callNumber='" + callNumber + '\'' +
                ", callTime='" + callTime + '\'' +
                ", callType='" + callType + '\'' +
                '}';
    }
}
