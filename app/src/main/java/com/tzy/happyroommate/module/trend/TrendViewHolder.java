package com.tzy.happyroommate.module.trend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.model.TrendModel;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.model.callback.StatusCallback;
import com.tzy.happyroommate.widget.FavorLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell on 2015/8/12.
 */
public class TrendViewHolder extends BaseViewHolder<Trend> {
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
    @InjectView(R.id.view_praise)
    ImageView viewPraise;
    @InjectView(R.id.view_comment)
    ImageView viewComment;
    @InjectView(R.id.view_perfect_praise)
    FavorLayout viewPerfectPraise;


    private Trend data;
    private TrendFragment trendFragment;

    public TrendViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_trend_detail);
        ButterKnife.inject(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TrendsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("trends", data);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
        images.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), TrendsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("trends", data);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
        viewPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPerfectPraise.addFavor();//点赞动画
                sendPraiseToTrend(data);

            }
        });

    }

    public void setData(Trend data) {
        this.data = data;
        if (data.getObjectId().isEmpty() || data == null) {
            trendFragment.stopRefresh();
        } else {
            face.setImageURI(Uri.parse(data.getTrendPic()));
            name.setText(data.getRoomName());
            content.setText(data.getContent());
            time.setText(data.getCreatedAt());
            praise.setText(String.valueOf(data.getPraiseCount()));
            comment.setText(String.valueOf(data.getCommentCount()));
            if (data.getImages() != null) {
                images.setAdapter(new NetImageListAdapter(itemView.getContext(), data.getImages()));//第一个参数context
            } else {
                images.setAdapter(null);

            }
        }
    }

    public void sendPraiseToTrend(Trend data){
            TrendModel.getInstance().sendPraise(data, new StatusCallback() {
                @Override
                public void success(String info) {
                    if(info.equals("like")){
                        showPraise();
                    }else {
                        showNotPraise();
                    }
                }
            });
    }

    public void showPraise(){
        viewPraise.setImageResource(R.mipmap.ic_thumb_up_green);
    }

    public void showNotPraise(){
        viewPraise.setImageResource(R.mipmap.ic_thumb_up_grey);
    }
}
