package com.xxx.mining.model.http.bean;

public class SelectCountyBean {

    private String zhName;
    private String enName;
    private String areaCode;
    private String language;
    private String localCurrency;
    private int sort;

    public String getZhName() {
        return zhName;
    }

    public String getAreaCode() {
        return "+" + areaCode;
    }

}
