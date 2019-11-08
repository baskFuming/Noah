package com.xxx.mining.model.http.bean;

import com.xxx.mining.model.utils.StringUtil;

import java.util.List;

public class RecordRechargeBean {


            /**
             * id : 2
             * userId : 17
             * coinId : 1
             * rechargeDate : 2019-11-08 16:24:57
             * amount : 1
             * blockNum : 1
             * fromAddress : 1
             * toAddress : 1
             * txRemarks : 1
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
                return  StringUtil.getSimpleDataFormatTime("yyyy-MM-dd", Long.parseLong(rechargeDate));
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


//    private int id;
//    private String unit;
//    private int orginIndex;
//    private String tableSymbol;
//    private int transferType;
//    private double amount;
//    private long time;
//
//    public String getUnit() {
//        return unit;
//    }
//
//    public String getAmount() {
//        return "+" + StringUtil.getMoney(amount);
//    }
//
//    public String getTime() {
//        return StringUtil.getSimpleDataFormatTime("yyyy-MM-dd HH:mm:ss", time);
//    }

}
