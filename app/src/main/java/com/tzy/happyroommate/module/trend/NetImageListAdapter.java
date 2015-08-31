package com.tzy.happyroommate.module.trend;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.utils.JUtils;

/**
 * Created by alien on 2015/7/30.
 */
public class NetImageListAdapter extends ArrayAdapter<String> {

    public NetImageListAdapter(Context context, String[] list) {
        super(context, 0);
        addAll(list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleDraweeView img = new SimpleDraweeView(getContext());
        img.setLayoutParams(new AbsListView.LayoutParams(JUtils.dip2px(150), JUtils.dip2px(150)));
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(getItem(position)!=null){
            img.setImageURI(Uri.parse(getItem(position)));
        }
        return img;
    }

}
