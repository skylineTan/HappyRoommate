package com.tzy.happyroommate.module.trend;

import android.os.Bundle;

import com.jude.beam.nucleus.manager.Presenter;
import com.tzy.happyroommate.model.TrendModel;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.model.callback.DataCallback;
import com.tzy.happyroommate.model.callback.StatusCallback;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tzy on 2015/8/12.
 */
public class TrendPresenter extends Presenter<TrendFragment> {
    private ArrayList<Trend> mArrayList = new ArrayList<Trend>();
    TrendViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        refresh();

    }

    @Override
    protected void onTakeView(TrendFragment view) {
        super.onTakeView(view);
        refresh();
        if (mArrayList.size() != 0) {
            getView().stopRefresh();
            getView().addDate(mArrayList);//一次性更新拿到的所有数据
        }
    }

    public void refresh() {
        TrendModel.getInstance().getAllTrend(new DataCallback<List<Trend>>() {
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

    public void loadMore(){
        TrendModel.getInstance().getAllTrend(new DataCallback<List<Trend>>() {
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

//    public void sendPraiseToTrend(Trend data){
//        TrendModel.getInstance().sendPraise(data, new StatusCallback() {
//            @Override
//            public void success(String info) {
//                if(info.equals("like")){
//                    viewHolder = new TrendViewHolder(getView());
//                    viewHolder.showPraise();
//                }else {
//                    viewHolder.showNotPraise();
//                }
//            }
//        });
//    }
}
