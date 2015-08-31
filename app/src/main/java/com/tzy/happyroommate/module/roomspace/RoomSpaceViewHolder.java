package com.tzy.happyroommate.module.roomspace;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.utils.JTimeTransform;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.module.trend.NetImageListAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell on 2015/8/24.
 */
public class RoomSpaceViewHolder extends BaseViewHolder<Trend> {
    @InjectView(R.id.face)
    SimpleDraweeView face;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.content)
    TextView content;
    @InjectView(R.id.images)
    GridView images;
    @InjectView(R.id.praise_main)
    TextView praise;
    @InjectView(R.id.comment_main)
    TextView comment;

    private Trend data;
    private RoomSpaceActivity activity;
    public RoomSpaceViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_trend_detail);
        ButterKnife.inject(this, itemView);

    }

    public void setData(Trend data) {
        this.data = data;
        if (data.getObjectId().isEmpty() || data == null) {
            activity.stopRefresh();
        } else {
            face.setImageURI(Uri.parse(data.getTrendPic()));
            name.setText(data.getRoomName());
            content.setText(data.getContent());
            time.setText(data.getCreatedAt());
            praise.setText(String.valueOf(data.getPraiseCount()));
            comment.setText(String.valueOf(data.getCommentCount()));
            if (data.getImages() != null) {
                images.setAdapter(new NetImageListAdapter(itemView.getContext(), data.getImages()));//第一个参数context
                images.setVisibility(View.VISIBLE);
            } else {
                images.setAdapter(null);
                images.setVisibility(View.INVISIBLE);
            }
        }
    }
}
