package com.tzy.happyroommate.module.article;

import android.os.Bundle;

import com.jude.beam.nucleus.manager.Presenter;
import com.tzy.happyroommate.model.bean.Article;
import com.tzy.happyroommate.model.bean.Trend;

/**
 * Created by skyline on 2015/8/28.
 */
public class ArticleDetailPresenter extends Presenter<ArticleDetailActivity>{
    private Article article;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        article = (Article) getView().getIntent().getSerializableExtra("articles");

    }

    @Override
    protected void onCreateView(ArticleDetailActivity view) {
        super.onCreateView(view);
        getView().setArticles(article);
    }
}
