package com.grew.sw.cashlawn.model;

import java.io.Serializable;

public class PublicIpData implements Serializable {
    private String first_ip;//公网ip	189.243.251.162

    @Override
    public String toString() {
        return "PublicIpData{" +
                "first_ip='" + first_ip + '\'' +
                '}';
    }

    public String getFirst_ip() {
        return first_ip;
    }

    public void setFirst_ip(String first_ip) {
        this.first_ip = first_ip;
    }
}

