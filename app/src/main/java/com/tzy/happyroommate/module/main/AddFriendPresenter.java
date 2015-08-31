package com.tzy.happyroommate.module.main;

import android.os.Bundle;

import com.jude.beam.nucleus.manager.Presenter;
import com.tzy.happyroommate.app.APP;
import com.tzy.happyroommate.model.TrendModel;
import com.tzy.happyroommate.model.UserModel;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.model.callback.DataCallback;
import com.tzy.happyroommate.module.trend.TrendFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dell on 2015/8/19.
 */
public class AddFriendPresenter extends Presenter<AddFriendActivity> {
    private AddFriendAdapter addFriendAdapter;
    private MyUser[] myUsers;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    public void query(int way, String info) {
        switch (way) {
            case 0:
                UserModel.getInstance().queryUserByObjectId(info, new DataCallback<MyUser>() {
                    @Override
                    public void success(String info, MyUser data) {
                        addFriendAdapter = new AddFriendAdapter(getView());
                        addFriendAdapter.add(data);
                        getView().showResult(addFriendAdapter);
                    }
                });
                break;
            case 1:
                UserModel.getInstance().queryUserByEmail(info, new DataCallback<List<MyUser>>() {
                    @Override
                    public void success(String info, List<MyUser> data) {
                        addFriendAdapter = new AddFriendAdapter(getView());
                        addFriendAdapter.addAll(data);
                        getView().showResult(addFriendAdapter);

                    }
                });
                break;
            case 2:
                UserModel.getInstance().queryUsersByUserName(info, new DataCallback<List<MyUser>>() {
                    @Override
                    public void success(String info, List<MyUser> data) {
                        addFriendAdapter = new AddFriendAdapter(getView());
                        addFriendAdapter.addAll(data);
                        getView().showResult(addFriendAdapter);
                    }
                });
                break;
            case 3:
                UserModel.getInstance().queryUsersByNickName(info, new DataCallback<List<MyUser>>() {
                    @Override
                    public void success(String info, List<MyUser> data) {
                        addFriendAdapter = new AddFriendAdapter(getView());
                        addFriendAdapter.addAll(data);
                        getView().showResult(addFriendAdapter);
                    }
                });
                break;
        }

    }


}
