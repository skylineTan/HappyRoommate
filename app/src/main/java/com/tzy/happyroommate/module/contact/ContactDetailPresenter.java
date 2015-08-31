package com.tzy.happyroommate.module.contact;

import android.os.Bundle;

import com.jude.beam.nucleus.manager.Presenter;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.model.AttentionModel;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.model.callback.StatusCallback;
import com.tzy.happyroommate.module.main.MainActivity;

/**
 * Created by tzy on 2015/8/20.
 */
public class ContactDetailPresenter extends Presenter<ContactDetailActivity>{
    private MyUser data;
    String from = "";
    String username = "";

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        data = (MyUser)getView().getIntent().getSerializableExtra("friends");
        from = getView().getIntent().getStringExtra("from");//me add other
        username = getView().getIntent().getStringExtra("username");
    }

    @Override
    protected void onCreateView(ContactDetailActivity view) {
        super.onCreateView(view);
        getView().setFriends(data);

    }

    /**
     *加好友cardView是否显示
     */
    public void show(){//me add other
        if(from.equals("me")||from.equals("contact")){
            getView().hideAdd();
        } else{
            getView().showAdd();

        }
    }

    public void addFriends(MyUser user){
        getView().showProgress("关注中");
        AttentionModel.getInstance().attentionOther(user, new StatusCallback() {
            @Override
            public void success(String info) {
                JUtils.Toast("关注成功");
                getView().dismissProgress();
            }

            @Override
            public void failure(String info) {
                super.failure(info);
                JUtils.Toast("关注失败");
                getView().dismissProgress();
            }
        });
    }
}
