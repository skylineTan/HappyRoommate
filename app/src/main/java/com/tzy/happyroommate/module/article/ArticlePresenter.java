package com.tzy.happyroommate.module.article;

import android.os.Bundle;

import com.jude.beam.nucleus.manager.Presenter;
import com.tzy.happyroommate.model.ArticleModel;
import com.tzy.happyroommate.model.TrendModel;
import com.tzy.happyroommate.model.bean.Article;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.model.callback.DataCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tzy on 2015/8/13.
 */
public class ArticlePresenter extends Presenter<ArticleFragment>{
    private ArrayList<Article> mArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        refresh();
    }

    @Override
    protected void onTakeView(ArticleFragment view) {
        super.onTakeView(view);
        refresh();
        if (mArrayList.size() != 0) {
            getView().stopRefresh();
            getView().addDate(mArrayList);//一次性更新拿到的所有数据
        }
    }

    @Override
    protected void onCreateView(ArticleFragment view) {
        super.onCreateView(view);
    }

    public void refresh(){
        ArticleModel.getInstance().getAllArticle(new DataCallback<List<Article>>() {
            @Override
            public void success(String info, List<Article> data) {
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
        ArticleModel.getInstance().getAllArticle(new DataCallback<List<Article>>() {
            @Override
            public void success(String info, List<Article> data) {
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
}
