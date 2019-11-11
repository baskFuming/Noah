package com.xxx.mining.model.http.bean;

import com.xxx.mining.model.utils.StringUtil;

import java.util.List;

public class RecordRechargeBean {

    /**
     * id : 20
     * userId : 20
     * coinId : 10000002
     * rechargeDate : 2019-11-10 14:47:51
     * amount : 100
     * blockNum : 28738
     * fromAddress : 0x17d9ba0522eb999b851e6faff845df82e8a8d27f
     * toAddress : 0x6214be31797c7d1702504f4ba2af37a5d24e78b7
     * txRemarks :
     */

    private int id;
    private int userId;
    private int coinId;
    private String rechargeDate;
    private int amount;
    private int blockNum;
    private String fromAddress;
    private String toAddress;
    private String txRemarks;

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

    public int getCoinId() {
        return coinId;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public String getRechargeDate() {
        return rechargeDate;
    }

    public void setRechargeDate(String rechargeDate) {
        this.rechargeDate = rechargeDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(int blockNum) {
        this.blockNum = blockNum;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getTxRemarks() {
        return txRemarks;
    }

    public void setTxRemarks(String txRemarks) {
        this.txRemarks = txRemarks;
    }

}
