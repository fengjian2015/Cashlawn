package com.grew.sw.cashlawn.model;

import java.io.Serializable;
import java.util.List;

public class UrlResponse implements Serializable {
    private int code;
    private String message;
    private List<Data> data;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data implements Serializable{
        public String protocalName;
        public int protocalType; //1.隐私页 2.用户服务 3.ContactsLicense agreement 4.information collection and use rules 5.privacy policy 6.teProtocolUrlResprm and conditions
        public String url;

        public String getProtocalName() {
            return protocalName;
        }

        public void setProtocalName(String protocalName) {
            this.protocalName = protocalName;
        }

        public int getProtocalType() {
            return protocalType;
        }

        public void setProtocalType(int protocalType) {
            this.protocalType = protocalType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "protocalName='" + protocalName + '\'' +
                    ", protocalType=" + protocalType +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
