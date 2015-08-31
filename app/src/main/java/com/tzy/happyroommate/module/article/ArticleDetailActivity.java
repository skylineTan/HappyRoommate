package com.tzy.happyroommate.module.article;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.app.BaseActivity;
import com.tzy.happyroommate.model.bean.Article;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by skyline on 2015/8/28.
 */
@RequiresPresenter(ArticleDetailPresenter.class)
public class ArticleDetailActivity extends BaseActivity<ArticleDetailPresenter> {

    @InjectView(R.id.view_acticle)
    SimpleDraweeView viewActicle;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @InjectView(R.id.appbar)
    AppBarLayout appbar;
    @InjectView(R.id.tv_acticle_detail)
    TextView tvActicleDetail;
    @InjectView(R.id.main_content)
    CoordinatorLayout mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.inject(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setArticles(final Article data) {
        viewActicle.setImageURI(Uri.parse(data.getPic()));
        tvActicleDetail.setText(data.getContent());

    }
}
