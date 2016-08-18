package com.kai.wisdom_scut.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.controller.adapter.MsgChatAdapter;
import com.kai.wisdom_scut.db.RealmDb;
import com.kai.wisdom_scut.model.ServiceMsg;
import com.kai.wisdom_scut.utils.ActivityUtils;
import com.kai.wisdom_scut.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.kai.wisdom_scut.db.RealmDb.addMsg;
import static com.kai.wisdom_scut.db.RealmDb.findResponse;
import static com.kai.wisdom_scut.db.RealmDb.getMsgs;
import static com.kai.wisdom_scut.db.RealmDb.realm;

/**
 * Created by tusm on 16/8/15.
 */

public class MessageActivity extends Activity {
    @BindView(R.id.msgListview)
    ListView msgListView;
    @BindView(R.id.send_msg)
    EditText sendMsg;
    @BindView(R.id.send_Btn)
    Button sendBtn;
    @BindView(R.id.message_title)
    TextView messageTitle;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.hide_keyboard)
    Button hideKeyboard;
    @BindView(R.id.chat_ll)
    LinearLayout chatLl;
    @BindView(R.id.show_keyboard)
    Button showKeyboard;
    @BindView(R.id.menu1)
    TextView menu1;
    @BindView(R.id.menu2)
    TextView menu2;
    @BindView(R.id.menu3)
    TextView menu3;
    @BindView(R.id.menu_ll)
    LinearLayout menuLl;

    private RealmResults<ServiceMsg> serviceMsgList;
    private MsgChatAdapter msgChatAdapter;
    private String serviceName;
    private String sendContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        realm = Realm.getDefaultInstance();
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        serviceName = getIntent().getStringExtra("serviceName");
        RealmDb.clearUnRead(serviceName);
    }

    private void initView() {
        messageTitle.setText(serviceName);

        //============================================listview设置=============================================
        serviceMsgList = getMsgs(serviceName);
        msgChatAdapter = new MsgChatAdapter(this, serviceMsgList);
        msgListView.setAdapter(msgChatAdapter);
        //监听数据变化
        serviceMsgList.addChangeListener(element -> msgChatAdapter.notifyDataSetChanged());
        //====================================================================================================
        /*
        todo:网络请求
         */


    }


    @OnClick(R.id.send_Btn)
    public void onClick() {
        sendContent = sendMsg.getText().toString();
        if (!TextUtils.isEmpty(sendContent)) {
            sendMsg.setText("");
            addMsg2List(1, sendContent);
            setResponse(sendContent);
        }
    }

    private void addMsg2List(int isSend, String sendContent) {
        ServiceMsg msg = new ServiceMsg();
        msg.setServiceName(serviceName);
        msg.setIsSend(isSend);
        msg.setServiceContent(sendContent);
        msg.setServiceTime(TimeUtils.getCurTimeMills());
        addMsg(msg);
    }

    private void setResponse(String sendContent) {
        Observable.just(sendContent)
                .map(ask -> findResponse(ask))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> addMsg2List(0, response));
    }

    @Override
    protected void onStop() {
        serviceMsgList.removeChangeListeners();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }


    @OnClick(R.id.back)
    public void back() {
        ActivityUtils.finishActivity(this);
    }

    @OnClick({R.id.hide_keyboard, R.id.show_keyboard, R.id.menu1, R.id.menu2, R.id.menu3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hide_keyboard:
                chatLl.setVisibility(View.GONE);
                menuLl.setVisibility(View.VISIBLE);
                break;
            case R.id.show_keyboard:
                chatLl.setVisibility(View.VISIBLE);
                menuLl.setVisibility(View.GONE);
                break;
            case R.id.menu1:
                break;
            case R.id.menu2:
                break;
            case R.id.menu3:
                break;
        }
    }
}
