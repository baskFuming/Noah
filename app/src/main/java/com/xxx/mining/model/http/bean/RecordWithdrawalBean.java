package com.xxx.mining.model.http.bean;

import java.util.List;

public class RecordWithdrawalBean {

    /**
     * id : 8
     * userId : 22
     * address : 0x6214be31797c7d1702504f4ba2af37a5d24e78b7
     * amount : 100.1
     * remark : 访问访问
     * fee : 0.01
     * getAmount : null
     * drawHash : 45235237327852782
     * status : 2
     * coinId : 10000006
     * createTime : 2019-11-10 14:57:38
     * lastUpdateTime : 2019-11-10 14:57:38
     */

    private int id;
    private int userId;
    private String address;
    private double amount;
    private String remark;
    private double fee;
    private Object getAmount;
    private String drawHash;
    private int status;
    private int coinId;
    private String createTime;
    private String lastUpdateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public Object getGetAmount() {
        return getAmount;
    }

    public void setGetAmount(Object getAmount) {
        this.getAmount = getAmount;
    }

    public String getDrawHash() {
        return drawHash;
    }

    public void setDrawHash(String drawHash) {
        this.drawHash = drawHash;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCoinId() {
        return coinId;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
