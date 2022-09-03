package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class ContactInfoBackModel implements Serializable {
    public String name;
    public String mobile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
