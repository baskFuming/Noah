package com.xxx.mining.model.http.bean;

public class NodePayBean {
        private Object id;
        private int userId;
        private String orderNum;
        private Object millNum;
        private int commodityId;
        private int num;
        private double dwttPrice;
        private double usdtPrice;
        private double price;
        private int status;
        private int type;
        private String createTime;
        private String lastUpdateTime;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public Object getMillNum() {
            return millNum;
        }

        public void setMillNum(Object millNum) {
            this.millNum = millNum;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public double getDwttPrice() {
            return dwttPrice;
        }

        public void setDwttPrice(double dwttPrice) {
            this.dwttPrice = dwttPrice;
        }

        public double getUsdtPrice() {
            return usdtPrice;
        }

        public void setUsdtPrice(double usdtPrice) {
            this.usdtPrice = usdtPrice;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }
}
