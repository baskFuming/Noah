package com.xxx.mining.model.http.bean;

public class MillDetailBean {

    private double currencyToday;
    private double DifficultyToday;
    private String millNum;
    private double currencyTotal;
    private String createTime;
    private int myCalculation;

    public double getCurrencyToday() {
        return currencyToday;
    }

    public double getDifficultyToday() {
        return DifficultyToday;
    }

    public int getDifficultyTodayProgress() {
        return (int) (DifficultyToday * 100);
    }

    public String getMillNum() {
        return millNum;
    }

    public double getCurrencyTotal() {
        return currencyTotal;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getMyCalculation() {
        return myCalculation;
    }

}
