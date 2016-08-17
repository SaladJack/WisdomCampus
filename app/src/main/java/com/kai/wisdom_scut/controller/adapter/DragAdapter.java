package com.kai.wisdom_scut.controller.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.controller.drag.DragHolderCallBack;
import com.kai.wisdom_scut.controller.drag.RecycleCallBack;
import com.kai.wisdom_scut.model.ServicePos;
import com.kai.wisdom_scut.utils.ActivityUtils;
import com.kai.wisdom_scut.view.activity.HomeActivity;
import com.kai.wisdom_scut.view.activity.MoreActivity;

import io.realm.RealmResults;


public class DragAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RealmResults<ServicePos> list;

    private RecycleCallBack mRecycleClick;
    public final static int NORMAL = 0;
    public final static int MORE = 1;
    private View view;
    public SparseArray<Integer> show = new SparseArray<>();

    @Override
    public int getItemViewType(int position) {
        return position == list.size() - 1 ? MORE : NORMAL ;
    }



    public DragAdapter(RecycleCallBack click, RealmResults<ServicePos> data) {
        this.list = data;
        this.mRecycleClick = click;
    }

    public void setData(RealmResults<ServicePos> data) {
        this.list = data;
    }


    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case NORMAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);
                return new DragHolder(view, mRecycleClick);
            case MORE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);
                return new MoreHolder(view);
        }
        return new MoreHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DragHolder)
        {
            ((DragHolder)holder).text.setText(list.get(position).getServiceName());
            ((DragHolder)holder).icon.setImageResource(list.get(position).getImgResId());
            if (null == show.get(position))
                ((DragHolder)holder).del.setVisibility(View.INVISIBLE);
            else
                ((DragHolder)holder).del.setVisibility(View.VISIBLE);
        }
        else if (holder instanceof MoreHolder)
        {
            ((MoreHolder)holder).text.setText(list.get(position).getServiceName());
            ((MoreHolder)holder).icon.setImageResource(list.get(position).getImgResId());
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    public class DragHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, DragHolderCallBack {

        public TextView text;
        public ImageView del;
        public RelativeLayout item;
        public ImageView icon;
        private RecycleCallBack mClick;

        public DragHolder(View itemView, RecycleCallBack click) {
            super(itemView);
            mClick = click;
            item = (RelativeLayout) itemView.findViewById(R.id.item);
            text = (TextView) itemView.findViewById(R.id.text);
            del = (ImageView) itemView.findViewById(R.id.del);
            icon = (ImageView) itemView.findViewById(R.id.service_icon);
            item.setOnClickListener(this);
            del.setOnClickListener(this);
        }

        @Override
        public void onSelect() {
            show.clear();
            show.put(getAdapterPosition(), getAdapterPosition());
            itemView.setBackgroundColor(Color.LTGRAY);
            del.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClear() {
            itemView.setBackgroundResource(R.drawable.service_item_view);
            notifyDataSetChanged();
        }

        @Override
        public void onClick(View v) {
            if (null != mClick) {
                show.clear();
                mClick.itemOnClick(getAdapterPosition(), v);
            }
        }
    }

    public class MoreHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView text;
        public ImageView icon;
        public ImageView del;
        public RelativeLayout item;

        public MoreHolder(View itemView) {
            super(itemView);
            item = (RelativeLayout) itemView.findViewById(R.id.item);
            text = (TextView) itemView.findViewById(R.id.text);
            del = (ImageView) itemView.findViewById(R.id.del);
            icon = (ImageView) itemView.findViewById(R.id.service_icon);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.item:
                    if (v.getContext() instanceof HomeActivity) {
                        Intent intent = new Intent((HomeActivity) v.getContext(), MoreActivity.class);
                        ActivityUtils.parseToActivity((HomeActivity) v.getContext(), intent);
                        break;
                    }
            }
        }
    }
}
