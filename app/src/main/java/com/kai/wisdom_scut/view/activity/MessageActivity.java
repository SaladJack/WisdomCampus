package com.kai.wisdom_scut.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.controller.adapter.MsgChatAdapter;
import com.kai.wisdom_scut.db.Constants;
import com.kai.wisdom_scut.db.RealmDb;
import com.kai.wisdom_scut.model.ServiceMsg;
import com.kai.wisdom_scut.network.api.SimsimiApi;
import com.kai.wisdom_scut.utils.ActivityUtils;
import com.kai.wisdom_scut.utils.TimeUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
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
    @BindView(R.id.menu0)
    TextView menu0;
    @BindView(R.id.menu1)
    TextView menu1;
    @BindView(R.id.menu2)
    TextView menu2;
    @BindView(R.id.menu_ll)
    LinearLayout menuLl;
    @BindView(R.id.menu3)
    TextView menu3;
    @BindView(R.id.menu4)
    TextView menu4;
    @BindView(R.id.more_service)
    TextView moreService;
    @BindView(R.id.webContent)
    WebView webContent;
    @BindView(R.id.sub_menu_ll)
    LinearLayout subMenuLl;
    @BindView(R.id.myProgressBar)
    ProgressBar myProgressBar;
    @BindView(R.id.webContent_ll)
    LinearLayout webContentLl;

    private RealmResults<ServiceMsg> serviceMsgList;
    private MsgChatAdapter msgChatAdapter;
    private String serviceName, sendContent;
    private SimsimiApi simsimiApi;
    private Retrofit retrofit;
    private String[] subServices;
    private TextView[] menus;
    private String url;

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
        subServices = Constants.Service.mapServices.get(serviceName);
        RealmDb.clearUnRead(serviceName);//清除未读记录
        menus = new TextView[]{menu0, menu1, menu2, menu3, menu4};
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

        if (subServices != null)
        setMenu(subServices.length);
        webContent.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });

        webContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Logger.d(newProgress);
                if (newProgress == 100) {
                    myProgressBar.setVisibility(View.GONE);
                } else {
                    if (View.INVISIBLE == myProgressBar.getVisibility()) {
                        myProgressBar.setVisibility(View.VISIBLE);
                    }
                    myProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        webContent.getSettings().setJavaScriptEnabled(true);
    }

    private void setMenu(int menuLength) {
        if (menuLength < 3) {
            subMenuLl.setVisibility(View.GONE);
            moreService.setVisibility(View.GONE);
        }
        for (int i = 0; i < menus.length; ++i) {
            if (i < menuLength)
                menus[i].setText(subServices[i]);
            else
                menus[i].setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webContent.canGoBack()) {
            webContent.goBack();// 返回前一个页面
            return true;
        }else if (keyCode == KeyEvent.KEYCODE_BACK && webContent.getVisibility() == View.VISIBLE && !webContent.canGoBack()){
            webContentLl.setVisibility(View.GONE);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void addMsg2List(int isSend, String sendContent) {
        ServiceMsg msg = new ServiceMsg();
        msg.setServiceName(serviceName);
        msg.setIsSend(isSend);
        msg.setServiceContent(sendContent);
        msg.setServiceTime(TimeUtils.getCurTimeMills());
        addMsg(msg);
    }

    private void setMachineResponse(String sendContent) {
        Observable.just(sendContent)
                .map(ask -> findResponse(ask))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> addMsg2List(0, response));
    }

    private void setNetResponse() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(Constants.Api.simiSimiBaseUrl)
                    .build();
            simsimiApi = retrofit.create(SimsimiApi.class);
        }
        simsimiApi.getResponse(sendContent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    try {
                        addMsg2List(0, responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, error -> setMachineResponse(sendContent));
    }

    @Override
    protected void onStop() {
        serviceMsgList.removeChangeListeners();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        realm.close();
        Logger.d("destroy");
        super.onDestroy();
    }

    @OnClick({R.id.hide_keyboard, R.id.show_keyboard, R.id.menu0, R.id.menu1, R.id.menu2, R.id.menu3, R.id.menu4, R.id.send_Btn, R.id.back, R.id.more_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hide_keyboard:
                chatLl.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_out));
                chatLl.setVisibility(View.GONE);
                menuLl.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_in));
                menuLl.setVisibility(View.VISIBLE);
                break;
            case R.id.show_keyboard:
                menuLl.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_out));
                menuLl.setVisibility(View.GONE);
                chatLl.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_in));
                chatLl.setVisibility(View.VISIBLE);
                break;
            case R.id.send_Btn:
                sendContent = sendMsg.getText().toString();
                if (!TextUtils.isEmpty(sendContent)) {
                    sendMsg.setText("");
                    addMsg2List(1, sendContent);
                    setNetResponse();
                }
                break;
            case R.id.back:
                ActivityUtils.finishActivity(this);
                break;
            case R.id.menu0:
                if (subServices != null && (url = Constants.Service.mapServiceUrl.get(subServices[0])) != null) {
                    webContentLl.setVisibility(View.VISIBLE);
                    webContent.loadUrl(url);
                }
                break;
            case R.id.menu1:
                if (subServices != null && (url = Constants.Service.mapServiceUrl.get(subServices[1])) != null) {
                    webContentLl.setVisibility(View.VISIBLE);
                    webContent.loadUrl(url);
                }
                break;
            case R.id.menu2:
                if (subServices != null && (url = Constants.Service.mapServiceUrl.get(subServices[2])) != null) {
                    webContentLl.setVisibility(View.VISIBLE);
                    webContent.loadUrl(url);
                }
                break;
            case R.id.menu3:
                if (subServices != null && (url = Constants.Service.mapServiceUrl.get(subServices[3])) != null) {
                    webContentLl.setVisibility(View.VISIBLE);
                    webContent.loadUrl(url);
                }
                break;
            case R.id.menu4:
                if (subServices != null && (url = Constants.Service.mapServiceUrl.get(subServices[4])) != null) {
                    webContentLl.setVisibility(View.VISIBLE);
                    webContent.loadUrl(url);
                }
                break;
            case R.id.more_service:
                if (subMenuLl.getVisibility() == View.VISIBLE)
                    subMenuLl.setVisibility(View.GONE);
                else {
                    subMenuLl.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
