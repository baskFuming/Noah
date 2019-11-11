package com.xxx.mining.model.http.bean;

import java.util.List;

public class WalletBean {

    private double totalAsset;
    private List<ListBean> list;

    public double getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(double totalAsset) {
        this.totalAsset = totalAsset;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * coinId : 10000001
         * amount : 1000000.0
         * address : 敬请期待
         * fee : 0.01
         * amountFrozen : 0.0
         * coinName : BTC
         */

        private int coinId;
        private double amount;
        private String address;
        private double fee;
        private double amountFrozen;
        private String coinName;
        private Integer isDisplay;

        public int getCoinId() {
            return coinId;
        }

        public void setCoinId(int coinId) {
            this.coinId = coinId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public boolean isDisplay() {
            return isDisplay == 1;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public double getAmountFrozen() {
            return amountFrozen;
        }

        public void setAmountFrozen(double amountFrozen) {
            this.amountFrozen = amountFrozen;
        }

        public String getCoinName() {
            return coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }
    }
}
