package com.tzy.happyroommate.model;

import android.content.Context;

import com.jude.beam.model.AbsModel;
import com.jude.utils.JUtils;

/**
 * Created by tzy on 2015/8/12.
 */
public class ChatModel extends AbsModel{
    public static ChatModel getInstance(){
        return getInstance(ChatModel.class);
    }

    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
    }


}
