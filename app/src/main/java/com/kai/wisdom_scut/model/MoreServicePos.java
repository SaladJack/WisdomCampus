package com.kai.wisdom_scut.model;

import com.kai.wisdom_scut.db.Constants;

import io.realm.RealmObject;

/**
 * Created by tusm on 16/8/17.
 */

public class MoreServicePos extends RealmObject {
    private String ServiceName;
    private int position;
    private int imgResId;

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getImgResId() {
        return Constants.Service.mapImg.get(getServiceName());
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }
}
