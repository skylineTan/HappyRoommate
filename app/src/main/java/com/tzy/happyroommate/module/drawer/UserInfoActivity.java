package com.tzy.happyroommate.module.drawer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.app.APP;
import com.tzy.happyroommate.app.BaseActivity;
import com.tzy.happyroommate.model.bean.MyUser;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobUser;

/**
 * Created by dell on 2015/8/21.
 */
@RequiresPresenter(UserInfoPresenter.class)
public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.face)
    SimpleDraweeView face;
    @InjectView(R.id.pic)
    RelativeLayout pic;
    @InjectView(R.id.name)
    LinearLayout name;
    @InjectView(R.id.sex)
    LinearLayout sex;
    @InjectView(R.id.city)
    LinearLayout city;
    @InjectView(R.id.roomName)
    LinearLayout roomName;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_sex)
    TextView tvSex;
    @InjectView(R.id.tv_city)
    TextView tvCity;
    @InjectView(R.id.tv_room_name)
    TextView tvRoomName;
    @InjectView(R.id.tv_email)
    TextView tvEmail;
    @InjectView(R.id.email)
    LinearLayout email;
    @InjectView(R.id.head_arrows)
    ImageView headArrows;
    @InjectView(R.id.tv_age)
    TextView tvAge;
    @InjectView(R.id.age)
    LinearLayout age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ButterKnife.inject(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();

    }

    private void init() {
        MyUser user = BmobUser.getCurrentUser(APP.getContext(),MyUser.class);
        pic.setOnClickListener(this);
        name.setOnClickListener(this);
        sex.setOnClickListener(this);
        city.setOnClickListener(this);
        roomName.setOnClickListener(this);
        age.setOnClickListener(this);
        email.setOnClickListener(this);
        tvEmail.setText(user.getEmail());
        tvRoomName.setText(user.getRoomName());
        face.setImageURI(Uri.parse(user.getPic()));
        tvName.setText(user.getNickName());
        tvCity.setText(user.getCity());
        if(user.isSex()==null) {
            tvSex.setText("");
        }else if(user.isSex()){
            tvSex.setText("女");
        }else {
            tvSex.setText("男");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.complete:
                getPresenter().updataUserInfo();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String getNickName() {
        return tvName.getText().toString();
    }

    public Boolean getSex() {
        if (tvSex.getText().toString() == "女")
            return true;
        else
            return false;
    }

    public String getCity() {
        return tvCity.getText().toString();
    }

    public String getRoomName() {
        return tvRoomName.getText().toString();
    }

    public String getEmail() {
        return tvEmail.getText().toString();
    }

    public Integer getAge() {
        if(tvAge.getText().toString().isEmpty()){
            return null;
        }else {
            return Integer.valueOf(tvAge.getText().toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.name:
                showUpdateNameDialog();
                break;
            case R.id.pic:
                showImageSelector();
                break;
            case R.id.age:
                showUpdateAgeDialog();
                break;
            case R.id.sex:
                showUpdateSexDialog();
                break;
            case R.id.city:
                showUpdateCityDialog();
                break;
            case R.id.roomName:
                showUpdateRoomNameDialog();
                break;
            case R.id.email:
                showUpdateEmailDialog();
                break;

        }

    }

    private void showUpdateNameDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.info_name)
                .input("请输入昵称", tvName.getText(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                        tvName.setText(charSequence);
                        materialDialog.dismiss();
                    }
                }).show();

    }

    public void showImageSelector() {
        new MaterialDialog.Builder(this)
                .title("请选择图片的来源")
                .items(new String[]{"拍照", "从手机相册选择", "网络"})
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        getPresenter().changeFace(i);
                    }
                }).show();
    }

    private void showUpdateAgeDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.info_age)
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .input("请输入年龄", tvAge.getText().toString(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                        tvAge.setText(charSequence);
                        materialDialog.dismiss();
                    }
                }).show();
    }

    private void showUpdateSexDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.info_sex)
                .items(new String[]{"男", "女"})
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        if (i == 0) {
                            tvSex.setText("男");

                        } else {
                            tvSex.setText("女");
                        }
                    }
                }).show();

    }

    private void showUpdateCityDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.info_city)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("请输入所在地", tvCity.getText().toString(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                        tvCity.setText(charSequence);
                        materialDialog.dismiss();
                    }
                }).show();

    }

    private void showUpdateEmailDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.info_emial)
                .input("请输入email", tvEmail.getText(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                        tvEmail.setText(charSequence);
                        materialDialog.dismiss();
                    }
                }).show();

    }


    private void showUpdateRoomNameDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.info_room_name)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("请输入寝室名字", tvRoomName.getText().toString(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                        tvRoomName.setText(charSequence);
                        materialDialog.dismiss();
                    }
                }).show();
    }

    public void showFace(Uri uri){
        face.setImageURI(uri);
    }
}
