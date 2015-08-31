package com.tzy.happyroommate.module.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.tagview.TAGView;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.app.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell on 2015/8/23.
 */
@RequiresPresenter(AddRoomPresenter.class)
public class AddRoomActivity extends BaseActivity<AddRoomPresenter> implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.view_search)
      TAGView viewSearch;
    @InjectView(R.id.recycler_room)
    EasyRecyclerView mRecyclerView;

    static EditText etInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_add);
        ButterKnife.inject(this);
        etInfo = (EditText) findViewById(R.id.et_id);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewSearch.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showQueryResult(RecyclerArrayAdapter adapter){
        mRecyclerView.setAdapter(adapter);
    }

    public static String getRoomIdInfo() {
        return etInfo.getText().toString();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_search:
                getPresenter().queryRoom(getRoomIdInfo());
                break;
        }

    }
}
