package com.xxx.mining.model.http.bean;

import java.util.List;

public class ShopMiningBean {

    /**
     * usdtPrice : 1800
     * img : null
     * name : 普通矿机
     * details : null
     * id : 1
     * dwttPrice : null
     */

    private int usdtPrice;
    private Object img;
    private String name;
    private Object details;
    private int id;
    private double dwttPrice;

    public int getUsdtPrice() {
        return usdtPrice;
    }

    public void setUsdtPrice(int usdtPrice) {
        this.usdtPrice = usdtPrice;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDwttPrice() {
        return dwttPrice;
    }

    public void setDwttPrice(double dwttPrice) {
        this.dwttPrice = dwttPrice;
    }
}
