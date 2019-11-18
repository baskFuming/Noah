package com.xxx.mining.model.http.bean;

public class NodeCommod {

    /**
     * usdtPrice : 1260.0
     * img : null
     * price : 1800.0
     * name : 节点矿机
     * details : 无动态权益,dwtt购买，数量不限
     * id : 2
     * dwttPrice : 540.6932656234397
     */

    private double usdtPrice;
    private int img;
    private double price;
    private String name;
    private String details;
    private int id;
    private double dwttPrice;

    public double getUsdtPrice() {
        return usdtPrice;
    }

    public void setUsdtPrice(double usdtPrice) {
        this.usdtPrice = usdtPrice;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
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
