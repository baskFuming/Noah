package com.xxx.mining.model.http.bean;


import java.util.List;

public class RecordTeamBean {

    private double totalIncome;
    private List<ListBean> list;

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 3
         * userId : 17
         * value : 100.0
         * inviterId : 18
         * coinId : 10000001
         * touchNum : 0
         * status : 0
         * createTime : 2019-11-09 11:49:31
         * lastUpdateTime : 2019-11-09 11:49:31
         */

        private int id;
        private int userId;
        private double value;
        private int inviterId;
        private int coinId;
        private int touchNum;
        private int status;
        private String createTime;
        private String lastUpdateTime;

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

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public int getInviterId() {
            return inviterId;
        }

        public void setInviterId(int inviterId) {
            this.inviterId = inviterId;
        }

        public int getCoinId() {
            return coinId;
        }

        public void setCoinId(int coinId) {
            this.coinId = coinId;
        }

        public int getTouchNum() {
            return touchNum;
        }

        public void setTouchNum(int touchNum) {
            this.touchNum = touchNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
}
