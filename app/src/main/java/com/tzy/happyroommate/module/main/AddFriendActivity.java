package com.tzy.happyroommate.module.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.tagview.TAGView;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.app.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tzy on 2015/8/19.
 */
@RequiresPresenter(AddFriendPresenter.class)
public class AddFriendActivity extends BaseActivity<AddFriendPresenter> implements OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.et_info)
    EditText etInfo;
    @InjectView(R.id.view_search)
    TAGView viewSearch;
    @InjectView(R.id.recycler_friends)
    EasyRecyclerView recyclerFriends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_add);
        ButterKnife.inject(this);
        init();
        initToolbar();
    }

    private void init() {
        recyclerFriends.setLayoutManager(new LinearLayoutManager(this));
        viewSearch.setOnClickListener(this);

    }

    private void initToolbar(){
        toolbar.setTitle("添加朋友");
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public String getFriendInfo() {
        return etInfo.getText().toString();
    }

    public void showResult(AddFriendAdapter addFriendAdapter){
        recyclerFriends.setAdapterWithProgress(addFriendAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_search:
                showQueryWay(getFriendInfo());
                break;
        }

    }

    public void showQueryWay(final String info){
        new MaterialDialog.Builder(this)
                .title("请选择查询方式")
                .items(new String[]{"id", "email", "用户名","昵称"})
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        getPresenter().query(i, info);
                    }
                }).show();
    }
}
