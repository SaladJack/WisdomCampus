package com.kai.wisdom_scut.model;

import io.realm.RealmObject;

/**
 * Created by tusm on 16/8/13.
 */

public class MachineMsg extends RealmObject {
    private String ask;
    private String response;

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
