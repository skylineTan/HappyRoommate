package com.tzy.happyroommate.module.roomspace;

import android.os.Bundle;

import com.jude.beam.nucleus.manager.Presenter;
import com.tzy.happyroommate.model.TrendModel;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.model.callback.DataCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2015/8/24.
 */
public class RoomSpacePresenter extends Presenter<RoomSpaceActivity>{
    private ArrayList<Trend> mArrayList = new ArrayList<Trend>();
    private MyUser data;
    String from = "";
    String username = "";

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        data = (MyUser)getView().getIntent().getSerializableExtra("friends");
        from = getView().getIntent().getStringExtra("from");//me add other
        username = getView().getIntent().getStringExtra("username");//拿到了data数据
        addTrends();
    }

    @Override
    protected void onTakeView(RoomSpaceActivity view) {
        super.onTakeView(view);
        addTrends();
        if (mArrayList.size() != 0) {
            getView().stopRefresh();
            getView().addDate(mArrayList);//一次性更新拿到的所有数据
        }
    }

    public void addTrends(){
        if(from.equals("contact")){
            refreshFriend();
        }else {
            refreshUser();
        }
    }

    public void moreTrends(){
        if(from.equals("contact")){
            loadMoreFriend();
        }else {
            loadMoreUser();
        }
    }

    public void refreshFriend() {
        TrendModel.getInstance().getFriendTrend(data,new DataCallback<List<Trend>>() {
            @Override
            public void success(String info, List<Trend> data) {
                if (getView() != null) {
                    if (data == null || data.size() == 0)
                        getView().stopRefresh();
                    else {
                        getView().stopRefresh();
                        getView().addDate(data);
                        mArrayList.clear();
                        mArrayList.addAll(data);
                    }
                }
            }

            @Override
            public void failure(String info) {
                super.failure(info);
            }
        });
    }

    public void refreshUser() {
        TrendModel.getInstance().getUserTrend(new DataCallback<List<Trend>>() {
            @Override
            public void success(String info, List<Trend> data) {
                if (getView() != null) {
                    if (data == null || data.size() == 0)
                        getView().stopRefresh();
                    else {
                        getView().stopRefresh();
                        getView().addDate(data);
                        mArrayList.clear();
                        mArrayList.addAll(data);
                    }
                }
            }

            @Override
            public void failure(String info) {
                super.failure(info);
            }
        });
    }

    public void loadMoreFriend(){
        TrendModel.getInstance().getFriendTrend(data, new DataCallback<List<Trend>>() {
            @Override
            public void success(String info, List<Trend> data) {
                if (getView() != null) {
                    if (data == null || data.size() == 0)
                        getView().stopLoadMore();
                    else if (data.size() == mArrayList.size()) {
                        getView().stopLoadMore();
                    } else {
                        getView().addDate(data);
                        mArrayList.addAll(data);
                    }
                }

            }
        });
    }

    public void loadMoreUser(){
        TrendModel.getInstance().getFriendTrend(data,new DataCallback<List<Trend>>() {
            @Override
            public void success(String info, List<Trend> data) {
                if (getView() != null) {
                    if (data == null || data.size() == 0)
                        getView().stopLoadMore();
                    else if(data.size()==mArrayList.size()){
                        getView().stopLoadMore();
                    } else {
                        getView().addDate(data);
                        mArrayList.addAll(data);
                    }
                }

            }
        });
    }
}
