package com.tzy.happyroommate.module.contact;


import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tzy.happyroommate.model.bean.MyUser;


import java.util.List;

/**
 * Created by dell on 2015/8/20.
 */
public class ContactAdapter extends RecyclerArrayAdapter<MyUser>{

    public ContactAdapter(Context context) {
        super(context);
    }

    public ContactAdapter(Context context, MyUser[] objects) {
        super(context, objects);
    }

    public ContactAdapter(Context context, List<MyUser> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ContactViewHolder(viewGroup);
    }
}
