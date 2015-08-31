package com.tzy.happyroommate.module.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.model.bean.MyUser;

import java.util.List;


/**
 * Created by tzy on 2015/8/19.
 */
public class AddFriendAdapter extends RecyclerArrayAdapter<MyUser> {
    public AddFriendAdapter(Context context, MyUser[] objects) {
        super(context, objects);

    }

    public AddFriendAdapter(Context context, List<MyUser> objects) {
        super(context, objects);
    }

    public AddFriendAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup viewGroup, int i) {
        return new AddFriendViewHolder(viewGroup);
    }

}
