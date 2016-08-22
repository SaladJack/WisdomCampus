package com.kai.wisdom_scut.model;



import com.kai.wisdom_scut.db.Constants;

import io.realm.RealmObject;

/**
 * Created by tusm on 16/8/3.
 */

public class ServiceMsg extends RealmObject implements Comparable<ServiceMsg>{


    /**
     * serviceName : 就业信息
     * serviceTitle : 校园招聘
     * serviceShortContent : 6月8日校园招聘信息推荐
     * serviceImageUrl : http://musicdata.baidu.com/data2/pic/d25fb9f6c06a362b9f5f67810edec2ad/267472925/267472925.jpg
     * serviceContentUrl : http://www.baidu.com
     * serviceTime : 400000
     */

    private String serviceName = "";
    private String serviceContent = "";
    private int imageResId ;
    private long serviceTime;
    private int isSend = 0;
    private boolean unRead = true; //未读为true 已读为false
    public int unReadNum = 0;

    @Override
    public String toString() {
        return "ServiceMsg{" +
                "serviceName='" + serviceName + '\'' +
                ", serviceContent='" + serviceContent + '\'' +
                ", imageResId=" + imageResId +
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

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public int getImageResId() {
        return Constants.Service.mapImg.get(getServiceName());
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
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

    public boolean isUnRead() {
        return unRead;
    }

    public void setUnRead(boolean unRead) {
        this.unRead = unRead;
    }

    public int getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(int unReadNum) {
        this.unReadNum = unReadNum;
    }

    @Override
    public int compareTo(ServiceMsg msg) {
        return Long.compare(msg.getServiceTime(),this.getServiceTime());
    }
}
