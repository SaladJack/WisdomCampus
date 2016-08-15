package com.kai.wisdom_scut.model;

import io.realm.RealmObject;

/**
 * Created by tusm on 16/8/3.
 */

public class ServiceMsg extends RealmObject{


    /**
     * serviceName : 就业信息
     * serviceTitle : 校园招聘
     * serviceShortContent : 6月8日校园招聘信息推荐
     * serviceImageUrl : http://musicdata.baidu.com/data2/pic/d25fb9f6c06a362b9f5f67810edec2ad/267472925/267472925.jpg
     * serviceContentUrl : http://www.baidu.com
     * serviceTime : 400000
     */

    private String serviceName;
    private String serviceTitle;
    private String serviceShortContent;
    private String serviceImageUrl;
    private String serviceContentUrl;
    private long serviceTime;
    private int isSend;

    @Override
    public String toString() {
        return "ServiceMsg{" +
                "serviceName='" + serviceName + '\'' +
                ", serviceTitle='" + serviceTitle + '\'' +
                ", serviceShortContent='" + serviceShortContent + '\'' +
                ", serviceImageUrl='" + serviceImageUrl + '\'' +
                ", serviceContentUrl='" + serviceContentUrl + '\'' +
                ", serviceTime=" + serviceTime +
                ", isSend=" + isSend +
                '}';
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getServiceShortContent() {
        return serviceShortContent;
    }

    public void setServiceShortContent(String serviceShortContent) {
        this.serviceShortContent = serviceShortContent;
    }

    public String getServiceImageUrl() {
        return serviceImageUrl;
    }

    public void setServiceImageUrl(String serviceImageUrl) {
        this.serviceImageUrl = serviceImageUrl;
    }

    public String getServiceContentUrl() {
        return serviceContentUrl;
    }

    public void setServiceContentUrl(String serviceContentUrl) {
        this.serviceContentUrl = serviceContentUrl;
    }

    public long getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(long serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }
}
