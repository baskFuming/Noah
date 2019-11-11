package com.xxx.mining.model.http.bean;

import java.util.List;

public class RecordGiftBean {


    /**
     * totalIncome : 0.0
     * touchNum : 1
     * depthAmount : 3600.0
     * widthAmount : 3600.0
     * list : []
     */

    private double totalIncome;
    private int touchNum;
    private double depthAmount;
    private double widthAmount;
    private List<DataBean> list;

    public String getTotalIncome() {
        return totalIncome + "";
    }

    public String getTouchNum() {
        return touchNum + "";
    }

    public String getDepthAmount() {
        return depthAmount + "";
    }

    public String getWidthAmount() {
        return widthAmount + "";
    }

    public List<DataBean> getList() {
        return list;
    }

    public void setList(List<DataBean> list) {
        this.list = list;
    }

    public static class DataBean {

        private double value;

        private String createTime;

        public String getAmount() {
            return "+" + value + "USDT";
        }

        public String getTime() {
            return createTime;
        }

    }
}
