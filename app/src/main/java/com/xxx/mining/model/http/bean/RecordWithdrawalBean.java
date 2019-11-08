package com.xxx.mining.model.http.bean;

import com.xxx.mining.model.utils.StringUtil;

public class RecordWithdrawalBean {

    private Integer id;

    private Integer userId;

    private String address;

    private Double amount;

    private String remark;

    private Double fee;

    private Double getAmount;

    private String drawHash;

    private Integer status;

    private Integer coinId;

    private String createTime;

    private String lastUpdateTime;

    public String getUnit() {
        return "";
    }

    public String getAmount() {
        return "-" + StringUtil.getMoney(amount);
    }

    public String getTime() {
        return createTime;
    }

}
