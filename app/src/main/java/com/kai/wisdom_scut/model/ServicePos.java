package com.kai.wisdom_scut.model;

import com.kai.wisdom_scut.db.Constants;

import io.realm.RealmObject;

/**
 * Created by tusm on 16/8/17.
 */

public class ServicePos extends RealmObject {
    private String serviceName;
    private int position;
    private int imgResId;

    @Override
    public String toString() {
        return "ServicePos{" +
                "imgResId=" + imgResId +
                ", position=" + position +
                ", serviceName='" + serviceName + '\'' +
                "}\n";
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getImgResId() {
        return Constants.Service.map.get(getServiceName());
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }
}
