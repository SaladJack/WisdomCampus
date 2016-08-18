package com.kai.wisdom_scut.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.db.RealmDb;
import com.kai.wisdom_scut.utils.ActivityUtils;
import com.kai.wisdom_scut.view.activity.CollectionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.kai.wisdom_scut.db.RealmDb.deleteUser;

/**
 * Created by tusm on 16/8/15.
 */

public class PersonFragment extends Fragment {

    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.id_iv)
    ImageView idIv;
    @BindView(R.id.person_info_container)
    RelativeLayout personInfoContainer;
    @BindView(R.id.card_ll)
    LinearLayout cardLl;
    @BindView(R.id.net_ll)
    LinearLayout netLl;
    @BindView(R.id.collection_ll)
    LinearLayout collectionLl;
    @BindView(R.id.settings_ll)
    LinearLayout settingsLl;
    @BindView(R.id.logout_ll)
    LinearLayout logoutLl;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.card_ll, R.id.net_ll, R.id.collection_ll, R.id.settings_ll,R.id.logout_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_ll:
                break;
            case R.id.net_ll:
                break;
            case R.id.collection_ll:
                Intent intent = new Intent(getContext(), CollectionActivity.class);
                ActivityUtils.parseToActivity(getActivity(),intent);
                break;
            case R.id.settings_ll:
                break;
            case R.id.logout_ll:
                deleteUser();
                ActivityUtils.finishActivity(getActivity());
                break;
        }
    }
}
