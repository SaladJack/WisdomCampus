package com.kai.wisdom_scut.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.controller.adapter.DragAdapter;
import com.kai.wisdom_scut.controller.adapter.MsgChatAdapter;
import com.kai.wisdom_scut.controller.drag.DragItemCallBack;
import com.kai.wisdom_scut.controller.drag.RecycleCallBack;
import com.kai.wisdom_scut.model.ServicePos;
import com.kai.wisdom_scut.utils.ActivityUtils;
import com.kai.wisdom_scut.view.activity.MessageActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.kai.wisdom_scut.db.RealmDb.*;

/**
 * Created by tusm on 16/8/15.
 */

public class ServiceFragment extends Fragment implements RecycleCallBack {

    @BindView(R.id.service0)
    LinearLayout service0;
    @BindView(R.id.service1)
    LinearLayout service1;
    @BindView(R.id.service2)
    LinearLayout service2;
    @BindView(R.id.service3)
    LinearLayout service3;
    @BindView(R.id.recycle)
    RecyclerView recycle;

    private DragAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private RealmResults<ServicePos> servicePoseList;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initData() {
        initServicePos();
        servicePoseList = getServicePos();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        recycle.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mAdapter = new DragAdapter(this, servicePoseList);
        mItemTouchHelper = new ItemTouchHelper(new DragItemCallBack(this));
        mItemTouchHelper.attachToRecyclerView(recycle);
        recycle.setAdapter(mAdapter);
    }


    @Override
    public void itemOnClick(int position, View view) {
        if (view.getId() == R.id.del) {
            deleteServicePos(position);
            mAdapter.setData(servicePoseList);
            mAdapter.notifyItemRemoved(position);

        } else {
            mAdapter.notifyDataSetChanged();
            Intent intent = new Intent(getActivity(), MessageActivity.class);
            intent.putExtra("serviceName",servicePoseList.get(position).getServiceName());
            ActivityUtils.parseToActivity(getActivity(),intent);

        }
    }

    @Override
    public void onMove(int from, int to) {
        synchronized (this) {
            if (from > to) {
                int count = from - to;
                for (int i = 0; i < count; i++) {
                    swapServicePos(from - i,from - i - 1);
                }
            }
            if (from < to) {
                int count = to - from;
                for (int i = 0; i < count; i++) {
                    swapServicePos(from + i, from + i + 1);
                }
            }
            mAdapter.setData(servicePoseList);
            mAdapter.notifyItemMoved(from, to);
            mAdapter.show.clear();
            mAdapter.show.put(to, to);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.service0, R.id.service1, R.id.service2, R.id.service3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.service0:
                break;
            case R.id.service1:
                break;
            case R.id.service2:
                break;
            case R.id.service3:
                break;
        }
    }
}
