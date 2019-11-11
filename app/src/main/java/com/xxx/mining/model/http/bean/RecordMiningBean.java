package com.xxx.mining.model.http.bean;

import com.xxx.mining.model.utils.StringUtil;

import java.util.List;

public class RecordMiningBean {

        private int totalIncome;
        private List<ListBean> list;

        public int getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(int totalIncome) {
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
             * date : 2019-11-08 22:30:45
             * millNum : 10000
             * value : 100
             */

            private String date;
            private String millNum;
            private int value;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getMillNum() {
                return millNum;
            }

            public void setMillNum(String millNum) {
                this.millNum = millNum;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }
        }
    }



//    return StringUtil.getSimpleDataFormatTime("yyyy-MM-dd HH:mm:ss",Long.parseLong(date));




