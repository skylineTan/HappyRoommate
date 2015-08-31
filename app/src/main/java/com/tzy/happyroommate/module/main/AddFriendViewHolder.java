package com.tzy.happyroommate.module.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.module.contact.ContactDetailActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tzy on 2015/8/19.
 */
public class AddFriendViewHolder extends BaseViewHolder<MyUser> {
    @InjectView(R.id.face)
    SimpleDraweeView face;
    @InjectView(R.id.nickName)
    TextView nickName;
    @InjectView(R.id.id_num)
    TextView idNum;
    @InjectView(R.id.person_id)
    LinearLayout personId;
    @InjectView(R.id.username)
    TextView username;
    @InjectView(R.id.cv_person_info)
    CardView cvPersonInfo;

    private MyUser data;

    public AddFriendViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_friends_add);//第二个参数传入item
        ButterKnife.inject(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ContactDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("friends", data);
                intent.putExtras(bundle);
                //自己和好友资料卡界面的显示与隐藏
                intent.putExtra("from", "add");
                intent.putExtra("username", data.getUsername());
                v.getContext().startActivity(intent);
            }
        });

    }

    public void setData(MyUser data) {
        this.data = data;
        nickName.setText(data.getNickName());
        idNum.setText(data.getObjectId());
        username.setText(data.getUsername());
        face.setImageURI(Uri.parse(data.getPic()));
    }


}
