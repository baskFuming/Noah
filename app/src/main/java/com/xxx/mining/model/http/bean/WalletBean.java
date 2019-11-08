package com.xxx.mining.model.http.bean;

import java.util.List;

public class WalletBean {
    private int totalAsset;
    private List<ListBean> list;

    public int getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(int totalAsset) {
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
         * address : 敬请期待
         * ammountFrozen : 0
         * coinName : BTC
         * ammount : 0
         */

        private int coinId;
        private String address;
        private int ammountFrozen;
        private String coinName;
        private int ammount;

        public int getCoinId() {
            return coinId;
        }

        public void setCoinId(int coinId) {
            this.coinId = coinId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getAmmountFrozen() {
            return ammountFrozen;
        }

        public void setAmmountFrozen(int ammountFrozen) {
            this.ammountFrozen = ammountFrozen;
        }

        public String getCoinName() {
            return coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public int getAmmount() {
            return ammount;
        }

        public void setAmmount(int ammount) {
            this.ammount = ammount;
        }
    }
}
