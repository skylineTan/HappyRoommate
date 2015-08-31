package com.tzy.happyroommate.module.main;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tzy.happyroommate.model.bean.Room;

/**
 * Created by dell on 2015/8/23.
 */
public class AddRoomAdapter extends RecyclerArrayAdapter<Room>{

    public AddRoomAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup viewGroup, int i) {
        return new AddRoomViewHolder(viewGroup);
    }
}
