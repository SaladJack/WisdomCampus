package com.kai.wisdom_scut.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.adapter.MsgListAdapter;
import com.kai.wisdom_scut.db.Constants;
import com.kai.wisdom_scut.model.ServiceMsg;
import com.kai.wisdom_scut.utils.ActivityUtils;
import com.kai.wisdom_scut.view.activity.MessageActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import io.realm.Realm;

import static com.kai.wisdom_scut.db.RealmDb.getMsgsByName;
import static com.kai.wisdom_scut.db.RealmDb.realm;
import static com.kai.wisdom_scut.db.RealmDb.saveMsgs;

/**
 * Created by tusm on 16/8/15.
 */

public class MessageFragment extends Fragment {


    @BindView(R.id.message_lv)
    ListView msg_listview;
    @BindView(R.id.search)
    LinearLayout search;
    private Unbinder unbinder;
    private List<ServiceMsg> msgList = new ArrayList<>();
    private MsgListAdapter msgListAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.e("onCreateView");
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }
    private void initData() {
        saveMsgs(Constants.serviceMsgData); //模拟网络


    }

    private void initView() {
        msg_listview.setEmptyView(search);
        msgListAdapter = new MsgListAdapter(getContext(), msgList);
        msg_listview.setAdapter(msgListAdapter);
    }

    @Override
    public void onStart() {
        msgList.clear();
        msgList.addAll(getMsgsByName());
        msgListAdapter.notifyDataSetChanged();
        realm = Realm.getDefaultInstance();
        super.onStart();
    }





    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }




    @OnItemClick(R.id.message_lv)
    void onItemClick(int position) {
        Logger.e("click");
        String serviceName = msgList.get(position).getServiceName();
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        intent.putExtra("serviceName",serviceName);
        ActivityUtils.parseToActivity(getActivity(),intent);

    }

}
