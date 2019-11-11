package com.xxx.mining.model.http.bean;

import java.util.List;

public class AddressBean {

    /**
     * address : 0xlllllllllllllllll
     * id : 8
     * coinName : USDT(OMNI)
     * remarks : Aavvsvs888
     */

    private String address;
    private int id;
    private String coinName;
    private String remarks;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

