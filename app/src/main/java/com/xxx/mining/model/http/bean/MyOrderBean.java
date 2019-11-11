package com.xxx.mining.model.http.bean;

public class MyOrderBean {

    /**
     * imgUrl : null
     * date : 2019-11-10 14:35:28
     * millNum : 1
     * millName : 普通矿机
     * orderNum : 1573367728207
     * millPrice : 1800.0
     * millDWTTPrice : 1799.5121
     * millUSDTPrice : 1800.0
     * type : 0
     * status : 0
     */

    private Object imgUrl;
    private String date;
    private int millNum;
    private String millName;
    private String orderNum;
    private double millPrice;
    private double millDWTTPrice;
    private double millUSDTPrice;
    private int type;
    private int status;

    public Object getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Object imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMillNum() {
        return millNum;
    }

    public void setMillNum(int millNum) {
        this.millNum = millNum;
    }

    public String getMillName() {
        return millName;
    }

    public void setMillName(String millName) {
        this.millName = millName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public double getMillPrice() {
        return millPrice;
    }

    public void setMillPrice(double millPrice) {
        this.millPrice = millPrice;
    }

    public double getMillDWTTPrice() {
        return millDWTTPrice;
    }

    public void setMillDWTTPrice(double millDWTTPrice) {
        this.millDWTTPrice = millDWTTPrice;
    }

    public double getMillUSDTPrice() {
        return millUSDTPrice;
    }

    public void setMillUSDTPrice(double millUSDTPrice) {
        this.millUSDTPrice = millUSDTPrice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
