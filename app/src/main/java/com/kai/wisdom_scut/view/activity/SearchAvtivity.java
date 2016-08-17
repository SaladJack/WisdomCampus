package com.kai.wisdom_scut.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tusm on 16/8/17.
 */

public class SearchAvtivity extends Activity {
    @BindView(R.id.search_Et)
    EditText searchEt;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.search_lv)
    ListView searchLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.search_Et, R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_Et:
                break;
            case R.id.cancel:
                ActivityUtils.finishActivity(this);
                break;
        }
    }
}
