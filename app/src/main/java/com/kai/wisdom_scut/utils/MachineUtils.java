package com.kai.wisdom_scut.utils;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;


import com.kai.wisdom_scut.db.RealmDb;
import com.kai.wisdom_scut.model.MachineMsg;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.kai.wisdom_scut.db.RealmDb.realm;

/**
 * Created by tusm on 16/8/14.
 */

public class MachineUtils {
    public static void setResponse(final Context context, final String ask, final TextView tv){
        Observable.just("machine.txt")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        RealmDb.saveMachineMsg(RealmDb.getJson(context,s));
                        return realm.where(MachineMsg.class).equalTo("ask",ask).findFirst().getResponse();

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "读取错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(String response) {
                        tv.setText(response);

                    }
                });
    }
}
