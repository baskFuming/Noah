package com.xxx.mining.model.http.bean;

import com.xxx.mining.model.utils.StringUtil;

public class DepositRecordBean {

    private int id;
    private String unit;
    private int orginIndex;
    private String tableSymbol;
    private int transferType;
    private double amount;
    private long time;

    public String getUnit() {
        return unit == null ? "" : unit.toUpperCase();
    }

    public String getAmount(String tag) {
        return tag + StringUtil.getMoney(amount);
    }

    public String getTime() {
        return StringUtil.getSimpleDataFormatTime("yyyy-MM-dd HH:mm:ss", time);
    }

}
