package com.tzy.happyroommate.module.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.app.BaseActivity;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.module.roomspace.RoomSpaceActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tzy on 2015/8/20.
 */
@RequiresPresenter(ContactDetailPresenter.class)
public class ContactDetailActivity extends BaseActivity<ContactDetailPresenter> {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.face)
    SimpleDraweeView face;
    @InjectView(R.id.room_space)
    LinearLayout roomSpace;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.user_name)
    TextView userName;
    @InjectView(R.id.id_num)
    TextView idNum;
    @InjectView(R.id.sex)
    TextView sex;
    @InjectView(R.id.city)
    TextView city;
    @InjectView(R.id.room_name)
    TextView roomName;
    @InjectView(R.id.age)
    TextView age;
    @InjectView(R.id.cv_friends_add)
    CardView cvFriendsAdd;

    private MyUser data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_detail);
        ButterKnife.inject(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        data = (MyUser)getIntent().getSerializableExtra("friends");
        init();
        getPresenter().show();

    }

    private void init() {
        cvFriendsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().addFriends(data);
            }
        });
        roomSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.getRoomId().isEmpty()){
                    JUtils.Toast("该好友没有创建寝室空间");
                } else {
                    Intent intent = new Intent(ContactDetailActivity.this, RoomSpaceActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("friends", data);
                    intent.putExtras(bundle);
                    intent.putExtra("from", "contact");
                    intent.putExtra("username", data.getUsername());
                    startActivity(intent);
                }
            }
        });
    }


    public void setFriends(MyUser data) {
        name.setText(data.getNickName());
        userName.setText(data.getUsername());
        idNum.setText(data.getObjectId());
        city.setText(data.getCity());
        roomName.setText(data.getRoomName());
        age.setText(data.getAge()+"");
        face.setImageURI(Uri.parse(data.getPic()));
    }

    public void showAdd(){
        cvFriendsAdd.setVisibility(View.VISIBLE);
    }

    public void hideAdd(){
        cvFriendsAdd.setVisibility(View.INVISIBLE);
    }

}
