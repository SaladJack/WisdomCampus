package com.kai.wisdom_scut.controller.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.model.Collection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by tusm on 16/8/9.
 */

public class CollectionAdapter extends ArrayAdapter{
    private Context context;
    private int resource;
    private RealmResults<Collection> collections;
    private Collection collection;
    private ViewHolder holder;

    public CollectionAdapter(Context context, int resource, RealmResults<Collection> collections) {
        super(context, resource, collections);
        this.context = context;
        this.resource = resource;
        this.collections = collections;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         collection = (Collection) getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(resource,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{  
            holder = (ViewHolder) convertView.getTag();
        }

        holder.service_image.setImageResource(collection.getImgResId());
        holder.service_name.setText(collection.getServiceName());
        holder.content_title.setText(collection.getCollectionContent());
        return convertView;
    }


    static class ViewHolder{
        @BindView(R.id.service_image)
        ImageView service_image;
        @BindView(R.id.collection_content_image)
        ImageView content_image;
        @BindView(R.id.service_name)
        TextView service_name;
        @BindView(R.id.collection_content_title)
        TextView content_title;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
