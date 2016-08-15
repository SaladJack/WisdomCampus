package com.kai.wisdom_scut.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import com.kai.wisdom_scut.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tusm on 16/8/15.
 */

public class MessageActivity extends Activity {
    @BindView(R.id.msg_history_lv)
    ListView msgHistoryLv;
    @BindView(R.id.sendMsg)
    EditText sendMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
    }
}
