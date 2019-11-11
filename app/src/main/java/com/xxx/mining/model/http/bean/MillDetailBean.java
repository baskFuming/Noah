package com.xxx.mining.model.http.bean;

public class MillDetailBean {

    private int currencyToday;
    private double DifficultyToday;
    private String millNum;
    private int currencyTotal;
    private String createTime;
    private int myCalculation;

    public int getCurrencyToday() {
        return currencyToday;
    }

    public void setCurrencyToday(int currencyToday) {
        this.currencyToday = currencyToday;
    }

    public double getDifficultyToday() {
        return DifficultyToday;
    }

    public void setDifficultyToday(double DifficultyToday) {
        this.DifficultyToday = DifficultyToday;
    }

    public String getMillNum() {
        return millNum;
    }

    public void setMillNum(String millNum) {
        this.millNum = millNum;
    }

    public int getCurrencyTotal() {
        return currencyTotal;
    }

    public void setCurrencyTotal(int currencyTotal) {
        this.currencyTotal = currencyTotal;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getMyCalculation() {
        return myCalculation;
    }

    public void setMyCalculation(int myCalculation) {
        this.myCalculation = myCalculation;
    }
}
