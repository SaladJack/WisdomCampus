package com.kai.wisdom_scut.controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.db.RealmDb;
import com.kai.wisdom_scut.model.ServiceMsg;
import com.kai.wisdom_scut.utils.TimeUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import code.qiao.com.tipsview.TipsView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tusm on 16/8/16.
 */

public class MsgListAdapter extends ArrayAdapter<ServiceMsg> {

    private List<ServiceMsg> msgs;
    private Context context;
    public MsgListAdapter(Context context, List<ServiceMsg> msgs) {
        super(context,0);
        this.context = context;
        this.msgs = msgs;
    }

    @Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public ServiceMsg getItem(int position) {
        return msgs.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ServiceMsg msg = getItem(position);
        ViewHolder viewHolder;
        if (view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.message_list_item,parent,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.msg_image.setImageResource(msg.getImageResId());
        viewHolder.msg_title.setText(msg.getServiceName());
        viewHolder.msg_content.setText(msg.getServiceContent());
        viewHolder.msg_time.setText(TimeUtils.milliseconds2String(msg.getServiceTime()));

        //设置未读消息数量
        Observable.just(msg.getServiceName())
                .map(s -> RealmDb.getUnReadNum(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(unReadNum -> {
                    if (unReadNum == 0)
                        viewHolder.msg_unread_num.setVisibility(View.GONE);
                    else
                    {
                        viewHolder.msg_unread_num.setVisibility(View.VISIBLE);
                        viewHolder.msg_unread_num.setText("" + unReadNum);
                    }

                });


        TipsView.create((Activity) view.getContext()).attach(viewHolder.msg_unread_num, new TipsView.DragListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onComplete() {
                //数据库未读设置为已读
                RealmDb.clearUnRead(msg.getServiceName());
            }

            @Override
            public void onCancel() {
            }
        });

        return view;
    }

    static class ViewHolder{
        @BindView(R.id.msg_image)
        ImageView msg_image;
        @BindView(R.id.msg_title)
        TextView msg_title;
        @BindView(R.id.msg_content)
        TextView msg_content;
        @BindView(R.id.msg_time)
        TextView msg_time;
        @BindView(R.id.msg_unread_num)
        TextView msg_unread_num;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }

    }
}
