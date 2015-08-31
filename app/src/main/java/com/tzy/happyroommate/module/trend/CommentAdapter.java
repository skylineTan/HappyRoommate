package com.tzy.happyroommate.module.trend;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tzy.happyroommate.model.bean.Comment;
import com.tzy.happyroommate.module.contact.ContactViewHolder;

/**
 * Created by skyline on 2015/8/28.
 */
public class CommentAdapter extends RecyclerArrayAdapter<Comment>{
    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommentViewHolder(viewGroup);
    }
}
