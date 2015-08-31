package com.tzy.happyroommate.module.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.balysv.materialripple.MaterialRippleLayout;
import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.app.BaseActivity;
import com.tzy.happyroommate.module.register.RegisterActivity;
import com.tzy.happyroommate.widget.FloatingActionButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tzy on 2015/8/15.
 */

@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.ed_login_mail)
    EditText edLoginName;
    @InjectView(R.id.ed_login_pass)
    EditText edLoginPass;
    @InjectView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @InjectView(R.id.rootLayout)
    CoordinatorLayout rootLayout;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.ripple)
    MaterialRippleLayout ripple;
    @InjectView(R.id.fab_register)
    FloatingActionButton fabRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        collapsingToolbarLayout.setTitle("微室");
        setToolBar(false);
        btnLogin.setOnClickListener(this);
        fabRegister.setOnClickListener(this);
        ripple.setOnClickListener(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rootLayout.getWindowToken(), 0);

    }

    public String getUsername() {
        return edLoginName.getText().toString();
    }

    public String getPassword() {
        return edLoginPass.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.btn_login:
                if (getUsername().isEmpty()) {
                    JUtils.Toast("请输入你的昵称");
                } else if (getPassword().isEmpty()) {
                    JUtils.Toast("请输入你的密码");
                } else {
                    getPresenter().login();
                }
        }
    }

}
