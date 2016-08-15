package com.kai.wisdom_scut.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import com.dd.CircularProgressButton;
import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.db.Constants;
import com.kai.wisdom_scut.db.RealmDb;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by tusm on 16/8/15.
 */

public class LoginActivity extends Activity {

    @BindView(R.id.login_Btn)
    CircularProgressButton login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        RealmDb.realm = Realm.getDefaultInstance();

        if (RealmDb.haveUser()) {
            Logger.e("User已有");
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

    }

    @OnClick(R.id.login_Btn)
    public void setLogin(){
        /*
           todo 网络请求
             */
        login.setProgress(50);
        RealmDb.saveUser(Constants.UserTestData,"123456");
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> login.setProgress(0));
        Observable.timer(100, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> {
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    finish();
                });

    }
}
