package com.kai.wisdom_scut.view.activity;

/**
 * Created by tusm on 16/8/18.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.controller.adapter.CollectionAdapter;
import com.kai.wisdom_scut.db.Constants;
import com.kai.wisdom_scut.model.Collection;
import com.kai.wisdom_scut.utils.ActivityUtils;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.kai.wisdom_scut.db.RealmDb.getCollections;
import static com.kai.wisdom_scut.db.RealmDb.realm;
import static com.kai.wisdom_scut.db.RealmDb.saveCollections;

/**
 * Created by tusm on 16/8/9.
 */

public class CollectionActivity extends Activity {

    @BindView(R.id.collection_lv)
    ListView collection_lv;
    @BindView(R.id.back)
    LinearLayout back;
    private RealmResults<Collection> collections;
    private CollectionAdapter collection_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        realm = Realm.getDefaultInstance();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        collections = getCollections();
        collections.addChangeListener(s -> collection_adapter.notifyDataSetChanged());
        collection_adapter = new CollectionAdapter(this, R.layout.collection_item, collections);
        collection_lv.setAdapter(collection_adapter);
    }

    private void initData() {
        saveCollections(Constants.CollectionTestData);
    }

    @OnClick(R.id.back)
    public void onClick() {
        ActivityUtils.finishActivity(this);
    }
}
