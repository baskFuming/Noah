package com.xxx.mining.model.http.bean;

public class UserInfo {


    /**
     * userName : 18310005980
     * value : 欢迎来到Noah钱包
     */
    private boolean node;
    private String userName;
    private String value;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setNode(boolean node) {
        this.node = node;
    }

    public boolean isNode() {
        return node;
    }
}
