package com.tzy.happyroommate.module.trend;

import android.os.Bundle;
import android.os.Handler;

import com.jude.beam.nucleus.manager.Presenter;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.model.ArticleModel;
import com.tzy.happyroommate.model.CommentModel;
import com.tzy.happyroommate.model.TrendModel;
import com.tzy.happyroommate.model.bean.Article;
import com.tzy.happyroommate.model.bean.Comment;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.model.callback.DataCallback;
import com.tzy.happyroommate.model.callback.StatusCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Jude on 2015/8/9.
 */
public class TrendsDetailPresenter extends Presenter<TrendsDetailActivity> {
    private Trend trend;
    private ArrayList<Comment> mArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        trend = (Trend) getView().getIntent().getSerializableExtra("trends");
        refresh();
    }

    @Override
    protected void onTakeView(TrendsDetailActivity view) {
        super.onTakeView(view);
        refresh();
        if (mArrayList.size() != 0) {
            getView().stopRefresh();
            getView().addDate(mArrayList);//一次性更新拿到的所有数据
        }
    }

    @Override
    protected void onCreateView(TrendsDetailActivity view) {
        super.onCreateView(view);
        getView().setTrends(trend);
    }

    public void sendCommentInfo(final String commentContent, final Trend data){
        getView().showProgress("正在更新");
                CommentModel.getInstance().sendComment(commentContent, data, new StatusCallback() {
                    @Override
                    public void success(String info) {
                        getView().dismissProgress();
                        refresh();

                    }
                });
    }

    public void refresh(){
        CommentModel.getInstance().getAllComment(trend, new DataCallback<List<Comment>>() {

            @Override
            public void success(String info, List<Comment> data) {
                JUtils.Log("在refresh里面" + data.size());
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
        });

    }

    public void loadMore(){
        CommentModel.getInstance().getAllComment(trend,new DataCallback<List<Comment>>() {
            @Override
            public void success(String info, List<Comment> data) {
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

    public void deleteTrendInfo(Trend trend){
        TrendModel.getInstance().deleteTrend(trend, new StatusCallback() {
            @Override
            public void success(String info) {
                JUtils.Toast("删除成功");

            }

            @Override
            public void failure(String info) {
                super.failure(info);
            }
        });
    }
}
