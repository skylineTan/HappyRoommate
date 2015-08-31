package com.tzy.happyroommate.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jude.beam.nucleus.manager.Presenter;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.model.UserModel;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.model.callback.DataCallback;
import com.tzy.happyroommate.module.main.MainActivity;

/**
 * Created by tzy on 2015/8/15.
 */
public class LoginPresenter extends Presenter<LoginActivity> {
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    public void login(){
        getView().showProgress("登录中");

        UserModel.getInstance().login(getView().getUsername(), getView().getPassword(), new DataCallback<MyUser>() {
            @Override
            public void success(String info, MyUser data) {
                getView().dismissProgress();
                getView().finish();
                getView().startActivity(new Intent(getView(),MainActivity.class));

            }

            @Override
            public void failure(String info) {
                super.failure(info);
                getView().dismissProgress();
            }
        });
    }
}
