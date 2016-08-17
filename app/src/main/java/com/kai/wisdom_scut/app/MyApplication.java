package com.kai.wisdom_scut.app;

import android.app.Application;

import com.kai.wisdom_scut.db.RealmDb;
import com.orhanobut.logger.Logger;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.kai.wisdom_scut.db.Constants.config;
import static com.kai.wisdom_scut.db.Constants.key;

/**
 * Created by tusm on 16/8/15.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        config = new RealmConfiguration.Builder(this).encryptionKey(key).build();
        Realm.setDefaultConfiguration(config);


        RealmDb.deleteAllData();

        Logger.e("realm已完成初始化");
    }
}
