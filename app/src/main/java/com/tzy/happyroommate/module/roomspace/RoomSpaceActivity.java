package com.tzy.happyroommate.module.roomspace;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.app.BaseActivity;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.module.trend.TrendAdapter;

import java.util.List;

/**
 * Created by tzy on 2015/8/24.
 */
@RequiresPresenter(RoomSpacePresenter.class)
public class RoomSpaceActivity extends BaseActivity<RoomSpacePresenter>{
    private static EasyRecyclerView mRecyclerView;
    private RoomSpaceAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_recyclerview);
        mRecyclerView = (EasyRecyclerView) findViewById(R.id.recyclerview);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapterWithProgress(mAdapter = new RoomSpaceAdapter(this));
        mRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().addTrends();
            }
        });
        mAdapter.setMore(R.layout.view_list_more, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getPresenter().moreTrends();
            }
        });
        mAdapter.setNoMore(R.layout.view_list_nomore);
    }

    public void stopRefresh(){
        mAdapter.clear();
    }

    public void addDate(List<Trend> trends){
        mAdapter.addAll(trends);
    }
    public void stopLoadMore(){
        mAdapter.stopMore();
    }
}
