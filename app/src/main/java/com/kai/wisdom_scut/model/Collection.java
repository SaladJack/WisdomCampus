package com.kai.wisdom_scut.model;

import io.realm.RealmObject;

/**
 * Created by tusm on 16/8/3.
 */

public class Collection extends RealmObject {
    private int serviceId;
    private String serviceName;
    private long collectTime;

    @Override
    public String toString() {
        return "Collection{" +
                "serviceId=" + serviceId +
                ", serviceName='" + serviceName + '\'' +
                ", collectTime=" + collectTime +
                '}';
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public long getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(int collectTime) {
        this.collectTime = collectTime;
    }
}
