package com.kai.wisdom_scut.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.model.ServiceMsg;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tusm on 16/8/15.
 */

public class MsgChatAdapter extends ArrayAdapter<ServiceMsg> {
    private Context context;
    private int resource;
    private List<ServiceMsg> serviceMsgs = new ArrayList<>();

    public MsgChatAdapter(Context context, int resource, List<ServiceMsg> objects, Context context1, int resource1, List<ServiceMsg> serviceMsgs) {
        super(context, resource, objects);
        this.context = context1;
        this.resource = resource1;
        this.serviceMsgs = serviceMsgs;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
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
}
