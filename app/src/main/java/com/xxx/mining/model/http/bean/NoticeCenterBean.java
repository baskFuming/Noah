package com.xxx.mining.model.http.bean;

public class NoticeCenterBean {

    /**
     * id : 1
     * name : 公测
     * content : 正式公测1.0.0
     * nameEn : 公测
     * contentEn : public test
     * status : 0
     * createTime : 2019-11-11 12:52:27
     * lastUpdateTime : 2019-11-11 12:52:27
     */

    private int id;
    private String name;
    private String content;
    private String nameEn;
    private String contentEn;
    private int status;
    private String createTime;
    private String lastUpdateTime;
    private boolean isCheck;

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getContentEn() {
        return contentEn;
    }

    public void setContentEn(String contentEn) {
        this.contentEn = contentEn;
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
