package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class JsBridgeCallBackModel implements Serializable {
    private String appAction;
    private String appActionId;
    private String errorMsg;
    private String appStatus;
    private String appData;

    public String getAppAction() {
        return appAction;
    }

    public void setAppAction(String appAction) {
        this.appAction = appAction;
    }

    public String getAppActionId() {
        return appActionId;
    }

    public void setAppActionId(String appActionId) {
        this.appActionId = appActionId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getAppData() {
        return appData;
    }

    public void setAppData(String appData) {
        this.appData = appData;
    }

    @Override
    public String toString() {
        return "JsBridgeCallBackModel{" +
                "appAction='" + appAction + '\'' +
                ", appActionId='" + appActionId + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", appStatus='" + appStatus + '\'' +
                ", appData='" + appData + '\'' +
                '}';
    }
}
