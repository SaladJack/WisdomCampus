package com.kai.wisdom_scut.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.model.ServiceMsg;
import com.kai.wisdom_scut.utils.TimeUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by tusm on 16/8/15.
 */

public class MsgChatAdapter extends ArrayAdapter<ServiceMsg> {
    private Context context;
    private RealmResults<ServiceMsg> serviceMsgs;
    private SendViewHolder sendViewHolder;
    private ReceiveViewHolder receiveViewHolder;

    public MsgChatAdapter(Context context,RealmResults<ServiceMsg> serviceMsgs) {
        super(context, 0);
        this.context = context;
        this.serviceMsgs = serviceMsgs;
    }


    @Nullable
    @Override
    public ServiceMsg getItem(int position) {
        return serviceMsgs.get(position);
    }

    @Override
    public int getCount() {
        return serviceMsgs.size();
    }

    public int getResource(ServiceMsg msg) {
        return msg.getIsSend() == 1 ? R.layout.message_send_item : R.layout.message_receive_item;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return serviceMsgs.get(position).getIsSend();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ServiceMsg msg = getItem(position);
        int isSend = getItemViewType(position);
        switch(isSend){
            //接收
            case 0:
                if (view == null)
                {
                    view = LayoutInflater.from(context).inflate(getResource(msg), parent, false);
                    receiveViewHolder = new ReceiveViewHolder(view);
                    view.setTag(receiveViewHolder);
                }
                else
                {
                    receiveViewHolder = (ReceiveViewHolder) view.getTag();
                }
                receiveViewHolder.receive_img.setImageResource(msg.getImageResId());
                receiveViewHolder.receive_content.setText(msg.getServiceContent());
                receiveViewHolder.receive_time.setText(TimeUtils.milliseconds2String(msg.getServiceTime()));
                break;
            //发送
            case 1:
                if (view == null)
                {
                    view = LayoutInflater.from(context).inflate(getResource(msg), parent, false);
                    sendViewHolder = new SendViewHolder(view);
                    view.setTag(sendViewHolder);
                }
                else
                {
                    sendViewHolder = (SendViewHolder) view.getTag();
                }
                sendViewHolder.send_img.setImageResource(msg.getImageResId());
                sendViewHolder.send_content.setText(msg.getServiceContent());
                sendViewHolder.send_time.setText(TimeUtils.milliseconds2String(msg.getServiceTime()));
                break;
        }

        return view;
    }

    static class ReceiveViewHolder {
        @BindView(R.id.receive_img)
        ImageView receive_img;
        @BindView(R.id.receive_content)
        TextView receive_content;
        @BindView(R.id.receive_time)
        TextView receive_time;

        public ReceiveViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class SendViewHolder {
        @BindView(R.id.send_img)
        ImageView send_img;
        @BindView(R.id.send_content)
        TextView send_content;
        @BindView(R.id.send_time)
        TextView send_time;

        public SendViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
