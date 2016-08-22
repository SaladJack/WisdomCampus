package com.kai.wisdom_scut.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.controller.adapter.MsgListAdapter;
import com.kai.wisdom_scut.db.Constants;
import com.kai.wisdom_scut.model.ServiceMsg;
import com.kai.wisdom_scut.utils.ActivityUtils;
import com.kai.wisdom_scut.view.activity.BaiduYuyinActivity;
import com.kai.wisdom_scut.view.activity.MessageActivity;
import com.kai.wisdom_scut.view.activity.SearchAvtivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.kai.wisdom_scut.db.RealmDb.getMsgsByName;
import static com.kai.wisdom_scut.db.RealmDb.saveMsgs;

/**
 * Created by tusm on 16/8/15.
 */

public class MessageFragment extends Fragment {
    @BindView(R.id.message_lv)
    ListView msg_listview;
    @BindView(R.id.search)
    LinearLayout search;
    @BindView(R.id.yuyin)
    ImageView yuyin;
    private Unbinder unbinder;
    private List<ServiceMsg> msgList = new ArrayList<>();
    private MsgListAdapter msgListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        msg_listview.addHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.search_head_layout, null, false));
        msgListAdapter = new MsgListAdapter(getContext(), msgList);
        msg_listview.setAdapter(msgListAdapter);
    }

    @Override
    public void onStart() {


        msgList.clear();
        msgList.addAll(getMsgsByName());
        msgListAdapter.notifyDataSetChanged();

        super.onStart();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnItemClick(R.id.message_lv)
    void onItemClick(int position) {
        if (position > 0) {
            String serviceName = msgList.get(position - 1).getServiceName();
            Intent intent = new Intent(getActivity(), MessageActivity.class);
            intent.putExtra("serviceName", serviceName);
            ActivityUtils.parseToActivity(getActivity(), intent);
        } else {
            Intent intent = new Intent(getActivity(), SearchAvtivity.class);
            ActivityUtils.parseToActivity(getActivity(), intent);
            Logger.e("search");
        }
    }

    @OnClick(R.id.yuyin)
    public void onClick() {
        ActivityUtils.parseToActivity(getActivity(),new Intent(getActivity(), BaiduYuyinActivity.class));
    }
}
