package com.tzy.happyroommate.module.register;

import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.nucleus.manager.Presenter;
import com.tzy.happyroommate.model.UserModel;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.model.callback.DataCallback;
import com.tzy.happyroommate.module.login.LoginActivity;


/**
 * Created by tzy on 2015/8/15.
 */
public class RegisterPresenter extends Presenter<RegisterActivity>{
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    public void register(){
        getView().showProgress("注册中");
        UserModel.getInstance().register(getView().getEmail(), getView().getName(), getView().getPassword(), new DataCallback<MyUser>() {
            @Override
            public void success(String info, MyUser data) {
                getView().dismissProgress();
                getView().startActivity(new Intent(getView(), LoginActivity.class));
                getView().finish();
                //注册成功，跳转到登陆界面
            }

            @Override
            public void failure(String info) {
                super.failure(info);
                getView().dismissProgress();
            }
        });
    }
}
