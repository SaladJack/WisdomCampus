package com.kai.wisdom_scut.model;

import com.kai.wisdom_scut.db.Constants;

import io.realm.RealmObject;

/**
 * Created by tusm on 16/8/3.
 */

public class Collection extends RealmObject {
    private String serviceName;
    private long collectTime;
    private int imgResId;
    private String collectionContent;


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

    public int getImgResId() {
        return Constants.Service.mapImg.get(getServiceName());
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public String getCollectionContent() {
        return collectionContent;
    }

    public void setCollectionContent(String collectionContent) {
        this.collectionContent = collectionContent;
    }
}
