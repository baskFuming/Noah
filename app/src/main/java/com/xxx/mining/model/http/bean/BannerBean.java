package com.xxx.mining.model.http.bean;

import java.io.Serializable;

public class BannerBean implements Serializable {

    private String serialNumber;
    private String name;
    private int sysAdvertiseLocation;
    private String startTime;
    private String endTime;
    private String url;
    private Object linkUrl;
    private String remark;
    private int status;
    private Object createTime;
    private Object content;
    private Object author;
    private int sort;

    public String getUrl() {
        return url;
    }

}