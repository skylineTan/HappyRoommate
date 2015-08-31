package com.tzy.happyroommate.module.article;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.model.ArticleModel;
import com.tzy.happyroommate.model.bean.Article;
import com.tzy.happyroommate.model.callback.StatusCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by skyline on 2015/8/27.
 */
public class ArticleViewHolder extends BaseViewHolder<Article> {

    @InjectView(R.id.view_pic)
    SimpleDraweeView viewPic;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_desc)
    TextView tvDesc;
    @InjectView(R.id.rl_article)
    RelativeLayout rlArticle;
    @InjectView(R.id.view_delete)
    TAGView viewDelete;

    private Article data;

    public ArticleViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_article);
        ButterKnife.inject(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("articles", data);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
        viewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteArticle(data);
            }
        });
    }



    @Override
    public void setData(Article data) {
        this.data = data;
        viewPic.setImageURI(Uri.parse(data.getPic()));
        tvTitle.setText(data.getTitle());
        tvDesc.setText(data.getDesc());
    }

    private void deleteArticle(Article data) {
        ArticleModel.getInstance().deleteArticle(data, new StatusCallback() {
            @Override
            public void success(String info) {
                JUtils.Toast("删除成功");

            }
        });
    }
}
