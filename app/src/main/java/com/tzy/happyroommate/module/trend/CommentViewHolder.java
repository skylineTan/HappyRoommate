package com.tzy.happyroommate.module.trend;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.config.Config;
import com.tzy.happyroommate.model.bean.Comment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by skyline on 2015/8/28.
 */
public class CommentViewHolder extends BaseViewHolder<Comment> {
    @InjectView(R.id.face)
    SimpleDraweeView face;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.date)
    TextView date;
    @InjectView(R.id.content)
    TextView content;

    private Comment data;

    public CommentViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_trend_comment);
        ButterKnife.inject(this, itemView);
    }

    @Override
    public void setData(Comment data) {
        this.data = data;
        face.setImageURI(Uri.parse(data.getCommentPic()));
        name.setText(data.getAuthorName());
        date.setText(data.getUpdatedAt());
        content.setText(data.getContent());
    }
}
