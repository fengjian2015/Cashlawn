package com.grew.sw.cashlawn.model;

import java.io.Serializable;
import java.util.List;

public class PublicDataResponse implements Serializable {
    private int code;
    private String message;
    private String data;

    @Override
    public String toString() {
        return "PublicDataResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
