package com.xxx.mining.model.http.bean;

public class RecordDepositBean {

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
