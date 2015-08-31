package com.tzy.happyroommate.module.register;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.balysv.materialripple.MaterialRippleLayout;
import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.app.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tzy on 2015/8/15.
 */

@RequiresPresenter(RegisterPresenter.class)
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements View.OnClickListener {


    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.ed_username)
    EditText edUsername;
    @InjectView(R.id.ed_password)
    EditText edPassword;
    @InjectView(R.id.ed_password_two)
    EditText edPasswordTwo;
    @InjectView(R.id.ed_email)
    EditText edEmail;
    @InjectView(R.id.ed_register)
    Button edRegister;
    @InjectView(R.id.ripple)
    MaterialRippleLayout ripple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        edRegister.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }

    public void init() {
    }

    public String getEmail(){
        return edEmail.getText().toString();
    }

    public String getPassword(){
        return edPassword.getText().toString();
    }

    public String getPassword2(){
        return edPasswordTwo.getText().toString();
    }

    public String getName(){
        return edUsername.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ed_register://id写错了
                if (getEmail().isEmpty()|| getPassword().isEmpty() || getPassword2().isEmpty()|| getName().isEmpty()) {
                    JUtils.Toast("请填写完整");
                } else if (!getPassword().equals(getPassword2())) {
                    JUtils.Toast("两次输入密码不一致");
                } else {
                    getPresenter().register();
                }

                break;
            case R.id.toolbar:
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void onDetachedFromWindow() {//会失去后台任务
        super.onDetachedFromWindow();
        destroyPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //发出信号presenter也应该被销毁
        destroyPresenter();
    }
}
