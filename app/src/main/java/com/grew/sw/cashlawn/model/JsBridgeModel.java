package com.grew.sw.cashlawn.model;

import java.io.Serializable;
import java.util.Map;

public class JsBridgeModel implements Serializable {
    private String appAction;
    private String appActionId;
    private Map<String,Object> appData;

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

    public Map<String, Object> getAppData() {
        return appData;
    }

    public void setAppData(Map<String, Object> appData) {
        this.appData = appData;
    }

    @Override
    public String toString() {
        return "JsBridgeModel{" +
                "appAction='" + appAction + '\'' +
                ", appActionId='" + appActionId + '\'' +
                ", appData=" + appData +
                '}';
    }
}
