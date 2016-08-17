package com.kai.wisdom_scut.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.controller.adapter.MoreDragAdapter;
import com.kai.wisdom_scut.controller.drag.DragItemCallBack;
import com.kai.wisdom_scut.controller.drag.RecycleCallBack;
import com.kai.wisdom_scut.model.MoreServicePos;
import com.kai.wisdom_scut.utils.ActivityUtils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.kai.wisdom_scut.db.RealmDb.*;


/**
 * Created by tusm on 16/8/17.
 */

public class MoreActivity extends Activity implements RecycleCallBack {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.more_recycle)
    RecyclerView moreRecycle;


    private RealmResults<MoreServicePos> moreServicePoseList;
    private MoreDragAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        initData();
        initView();
    }


    private void initData() {
        moreServicePoseList = getMoreServicePos();
    }

    private void initView() {
        moreRecycle.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter = new MoreDragAdapter(this, moreServicePoseList);
        mItemTouchHelper = new ItemTouchHelper(new DragItemCallBack(this));
        mItemTouchHelper.attachToRecyclerView(moreRecycle);
        moreRecycle.setAdapter(mAdapter);
    }

    @Override
    public void itemOnClick(int position, View view) {
        if (view.getId() == R.id.more_del) {
            deleteMoreServicePos(position);
            mAdapter.setData(moreServicePoseList);
            mAdapter.notifyItemRemoved(position);

        } else {
            Logger.e(moreServicePoseList.get(position).getServiceName());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onMove(int from, int to) {
        synchronized (this) {
            if (from > to) {
                int count = from - to;
                for (int i = 0; i < count; i++) {
                    swapMoreServicePos(from - i,from - i - 1);
                }
            }
            if (from < to) {
                int count = to - from;
                for (int i = 0; i < count; i++) {
                    swapMoreServicePos(from + i, from + i + 1);
                }
            }
            mAdapter.setData(moreServicePoseList);
            mAdapter.notifyItemMoved(from, to);
            mAdapter.show.clear();
            mAdapter.show.put(to, to);
        }
    }


    @OnClick(R.id.back)
    public void onClick() {
        ActivityUtils.finishActivity(this);
    }
}
