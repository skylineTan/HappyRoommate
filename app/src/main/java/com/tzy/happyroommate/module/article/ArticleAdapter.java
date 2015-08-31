package com.tzy.happyroommate.module.article;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tzy.happyroommate.model.bean.Article;
import com.tzy.happyroommate.module.main.AddRoomViewHolder;

/**
 * Created by skyline on 2015/8/27.
 */
public class ArticleAdapter extends RecyclerArrayAdapter<Article> {
    public ArticleAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ArticleViewHolder(viewGroup);
    }
}
