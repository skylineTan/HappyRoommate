package com.tzy.happyroommate.module.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.utils.JUtils;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.app.APP;
import com.tzy.happyroommate.app.BaseActivity;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.module.drawer.AboutActivity;
import com.tzy.happyroommate.module.drawer.UserInfoActivity;
import com.tzy.happyroommate.module.login.LoginActivity;
import com.tzy.happyroommate.widget.CustomPrimaryDrawerItem;

import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobUser;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BaseActivity<MainPresenter> implements EventListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.container)
    FrameLayout container;
    @InjectView(R.id.chat)
    LinearLayout chat;
    @InjectView(R.id.contact)
    LinearLayout contact;
    @InjectView(R.id.trend)
    LinearLayout trend;
    @InjectView(R.id.note)
    LinearLayout note;
    @InjectView(R.id.iv_square)
    ImageView ivSquare;

    private static final int PROFILE_FIRST = 0;
    private static final int PROFILE_CHANGE = 3;
    private static final int PROFILE_MANAGER = 4;

    private AccountHeader headerResult = null;
    private Drawer result = null;
    private IProfile profile;//相当于用户
    private static MyUser user;
    private String roomName;
    private String roomId;

    //执行到mainActivity才能得到当前对象
    public static MyUser getUser() {
        user = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
        return user;
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FontHelper.applyFont(this, findViewById(R.id.activity_main_root), "fonts/ChanticleerRomanNF.ttf");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);//设置没有返回图标,有可能导致空指针异常
        //setSwipeBackEnable(false);
        ButterKnife.inject(this);
        initFragment();


        //抽屉，需要传递bundle，所以没有放在一个方法里面
        profile = new ProfileDrawerItem().withName(getUser().getUsername()).withEmail(getUser().getEmail()).withIcon(getUser().getPic()).withIdentifier(PROFILE_FIRST);
        buildHeader(false, savedInstanceState);
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_room_name).withIcon(FontAwesome.Icon.faw_home).withTag("0"),
                        new CustomPrimaryDrawerItem().withName("寝室资料").withIcon(FontAwesome.Icon.faw_bar_chart).withTag("1"),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_set_info).withIcon(FontAwesome.Icon.faw_bar_chart).withTag("2"),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_birthday).withIcon(FontAwesome.Icon.faw_calendar).withTag("3"),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_secret).withIcon(FontAwesome.Icon.faw_eye).withTag("4"),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_secret_space).withIcon(FontAwesome.Icon.faw_star).withTag("5"),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_setting).withIcon(FontAwesome.Icon.faw_cog).withTag("6"))
                .addStickyDrawerItems(new CustomPrimaryDrawerItem().withBackgroundRes(R.color.colorPrimary).withName(R.string.drawer_item_sign_out).withIcon(FontAwesome.Icon.faw_bell).withTag("7")
                )
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        MainActivity.this.finish();
                        return true;
                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        if (iDrawerItem != null && iDrawerItem instanceof Nameable) {
                            if (iDrawerItem.getTag().equals("0")) {
                                showEditRoomName();
                            } else if (iDrawerItem.getTag().equals("1")) {
                                showRoomInfoDialog();
                            } else if (iDrawerItem.getTag().equals("2")) {
                                startActivity(new Intent(MainActivity.this, UserInfoActivity.class));

                            } else if (iDrawerItem.getTag().equals("3")) {
                                JUtils.Toast("对不起，该模块只对帅的人开放");

                            } else if (iDrawerItem.getTag().equals("4")) {
                                JUtils.Toast("对不起，该模块只对帅的人开放");

                            } else if (iDrawerItem.getTag().equals("5")) {
                                JUtils.Toast("对不起，该模块只对帅的人开放");
                            } else if (iDrawerItem.getTag().equals("6")) {
                                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                            } else if (iDrawerItem.getTag().equals("7")) {
                                finish();
                            } else {
                                return true;
                            }
                        }
                        return false;
                    }
                })
                .withAnimateDrawerItems(true)
                .withSavedInstance(savedInstanceState)
                .build();
    }

    public void initFragment() {
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().showChatFragment();
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().showContactFragment();
                refresh();
            }
        });
        trend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().showTrendFragment();
            }
        });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().showNoteFragment();
            }
        });
        // liveTime = showLiveTime();

    }


    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }


    private void buildHeader(boolean compact, Bundle savedInstanceState) {
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.mipmap.mat1)
                .withCompactStyle(compact)
                .addProfiles(
                        profile,
                        new ProfileSettingDrawerItem().withName("添加账号").withDescription("Add new GitHub Account").withIcon(GoogleMaterial.Icon.gmd_add).withIdentifier(PROFILE_CHANGE),
                        new ProfileSettingDrawerItem().withName("管理账号").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(PROFILE_MANAGER)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
                        if (iProfile instanceof IDrawerItem && ((IDrawerItem) iProfile).getIdentifier() == PROFILE_CHANGE) {
                            BmobUser.logOut(MainActivity.this);
                            //点击添加账号，跳转到登陆界面
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        } else if (iProfile.getIdentifier() == PROFILE_FIRST) {
                            showImageSelector();

                        } else {
                            JUtils.Toast("对不起，该模块只对帅的人开放");
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
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


    private void showEditRoomName() {
        new MaterialDialog.Builder(this)
                .title(R.string.main_room_name)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("请输入您要修改的寝室名", roomName, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                        getPresenter().updateRoomNameInfo(charSequence.toString());
                    }
                }).show();
    }

    private void showRoomInfoDialog() {
        roomName = getUser().getRoomName();
        roomId = getUser().getRoomId();
        if (roomName == null) {
            roomName = "未创建寝室";
        } else if (roomId == null) {
            roomId = "";
        }
        new MaterialDialog.Builder(this)
                .title(R.string.main_room_info)
                .items(new String[]{"寝室名字：" + roomName, "寝室id：" + roomId})
                .positiveText(R.string.dialog_positive)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void addImageView(Uri uri) {//传过来图片url
        profile.setIcon(uri);
        headerResult.updateProfileByIdentifier(profile);//更新profile
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_1:
                startActivity(new Intent(this, AddFriendActivity.class));
                return true;
            case R.id.menu_2://添加寝室
                startActivity(new Intent(this, AddRoomActivity.class));
                return true;
            case R.id.menu_3://创建寝室
                showCreateDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showCreateDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.create_room)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("请输入要创建的寝室名", roomName, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                        getPresenter().createRoom(charSequence.toString());
                    }
                }).show();
    }

    private void refresh() {
        getPresenter().updateFansInfo();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = result.saveInstanceState(outState);
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    //再按一次退出程序
    private static long firstTime;

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
            getPresenter().showChatFragment();
        } else if (firstTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            JUtils.Toast("再按一次退出程序");
        }
        firstTime = System.currentTimeMillis();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}


