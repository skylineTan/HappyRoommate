package com.tzy.happyroommate.module.roomspace;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tzy.happyroommate.model.bean.Trend;

/**
 * Created by dell on 2015/8/24.
 */
public class RoomSpaceAdapter extends RecyclerArrayAdapter<Trend>{
    public RoomSpaceAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RoomSpaceViewHolder(viewGroup);
    }
}
