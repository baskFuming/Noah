package com.xxx.mining.model.http.bean;

import java.util.List;

public class DepositInfoBean {
    /**
     * date : 2019-11-07 20:54:10
     * coinName : BTC
     * tocoinName : MSB
     * value : 100.1
     */

    private String date;
    private String coinName;
    private String tocoinName;
    private double value;

    public String getDate() {
        return date;
    }


    public String getCoinName() {
        return coinName;
    }


    public String getTocoinName() {
        return tocoinName;
    }


    public double getValue() {
        return value;
    }

}
