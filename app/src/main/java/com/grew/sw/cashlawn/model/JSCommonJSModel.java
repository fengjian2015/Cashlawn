package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class JSCommonJSModel implements Serializable {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "JSCommonJSModel{" +
                "value='" + value + '\'' +
                '}';
    }
}
