package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class VersionResponse implements Serializable {
    private int code;
    private String message;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable{
        public String code;//版本号
        public int must;//是否强制更新 1强制 0建议升级
        public String url;
        public String tips;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getMust() {
            return must;
        }

        public void setMust(int must) {
            this.must = must;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "code='" + code + '\'' +
                    ", must=" + must +
                    ", url='" + url + '\'' +
                    ", tips='" + tips + '\'' +
                    '}';
        }
    }
}
