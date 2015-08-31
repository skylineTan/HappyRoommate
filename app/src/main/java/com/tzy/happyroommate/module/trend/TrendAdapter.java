package com.tzy.happyroommate.module.trend;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tzy.happyroommate.model.bean.Trend;

/**
 * Created by tzy on 2015/8/12.
 */
public class TrendAdapter extends RecyclerArrayAdapter<Trend>{
    public TrendAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup viewGroup, int i) {
        return new TrendViewHolder(viewGroup);
    }
}
