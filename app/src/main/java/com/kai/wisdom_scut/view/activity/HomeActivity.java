package com.kai.wisdom_scut.view.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.db.RealmDb;
import com.kai.wisdom_scut.model.MachineMsg;
import com.kai.wisdom_scut.view.fragment.MessageFragment;
import com.kai.wisdom_scut.view.fragment.PersonFragment;
import com.kai.wisdom_scut.view.fragment.ServiceFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import rx.Observable;
import rx.schedulers.Schedulers;

import static com.kai.wisdom_scut.db.RealmDb.realm;

/**
 * Created by tusm on 16/8/15.
 */

public class HomeActivity extends FragmentActivity {
    @BindView(R.id.message_img)
    ImageView messageImg;
    @BindView(R.id.message_tv)
    TextView messageTv;
    @BindView(R.id.message_ll)
    LinearLayout messageLl;
    @BindView(R.id.service_img)
    ImageView serviceImg;
    @BindView(R.id.service_tv)
    TextView serviceTv;
    @BindView(R.id.service_ll)
    LinearLayout serviceLl;
    @BindView(R.id.person_img)
    ImageView personImg;
    @BindView(R.id.tv_person)
    TextView personTv;
    @BindView(R.id.person_ll)
    LinearLayout personLl;
    @BindView(R.id.viewpager)
    ViewPager fragmentViewPager;
    @BindColor(R.color.main_blue)
    int selectedColor;
    @BindColor(R.color.unselectedColor)
    int unselectedColor;
    @BindDrawable(R.mipmap.message_normal)
    Drawable message_normal;
    @BindDrawable(R.mipmap.message_selected)
    Drawable message_selected;
    @BindDrawable(R.mipmap.service_normal)
    Drawable service_normal;
    @BindDrawable(R.mipmap.service_selected)
    Drawable service_selected;
    @BindDrawable(R.mipmap.person_normal)
    Drawable person_normal;
    @BindDrawable(R.mipmap.person_selected)
    Drawable person_selected;

    private FragmentPagerAdapter fragAdapter;
    private List<Fragment> fragList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        initData();
        initView();
    }

    private void initData() {
        fragList.add(new MessageFragment());
        fragList.add(new ServiceFragment());
        fragList.add(new PersonFragment());
        loadMachineFile();

    }

    private void initView() {
        fragAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragList.get(position);
            }

            @Override
            public int getCount() {
                return fragList.size();
            }
        };

        fragmentViewPager.setAdapter(fragAdapter);
        fragmentViewPager.setOnPageChangeListener(vpSlide);
    }

    private void loadMachineFile() {
        if(realm.where(MachineMsg.class).findFirst() == null) {
            Observable.just("machine.txt")
                    .observeOn(Schedulers.io())
                    .subscribe(filename -> RealmDb.saveMachineMsg(this, filename));
        }
    }


    @OnClick({R.id.message_ll, R.id.service_ll, R.id.person_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message_ll:
                fragmentViewPager.setCurrentItem(0);
                changeSelection(0);
                break;
            case R.id.service_ll:
                fragmentViewPager.setCurrentItem(1);
                changeSelection(1);
                break;
            case R.id.person_ll:
                fragmentViewPager.setCurrentItem(2);
                changeSelection(2);

                break;
        }
    }

    public ViewPager.OnPageChangeListener vpSlide = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            if (fragmentViewPager.getCurrentItem() == 0) {
                changeSelection(0);
            } else if (fragmentViewPager.getCurrentItem() == 1) {
                changeSelection(1);
            } else if (fragmentViewPager.getCurrentItem() == 2) {
                changeSelection(2);
            }
        }
    };

    /*
    设置选项颜色变化
     */
    private void changeSelection(int selectedIndex){
        switch (selectedIndex){
            case 0:
                messageTv.setTextColor(selectedColor);
                serviceTv.setTextColor(unselectedColor);
                personTv.setTextColor(unselectedColor);

                messageImg.setImageDrawable(message_selected);
                serviceImg.setImageDrawable(service_normal);
                personImg.setImageDrawable(person_normal);
                break;
            case 1:
                messageTv.setTextColor(unselectedColor);
                serviceTv.setTextColor(selectedColor);
                personTv.setTextColor(unselectedColor);

                messageImg.setImageDrawable(message_normal);
                serviceImg.setImageDrawable(service_selected);
                personImg.setImageDrawable(person_normal);
                break;
            case 2:
                messageTv.setTextColor(unselectedColor);
                serviceTv.setTextColor(unselectedColor);
                personTv.setTextColor(selectedColor);

                messageImg.setImageDrawable(message_normal);
                serviceImg.setImageDrawable(service_normal);
                personImg.setImageDrawable(person_selected);
                break;
        }
    }
}



