package com.tzy.happyroommate.module.article;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.balysv.materialripple.MaterialRippleLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.app.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by skyline on 2015/8/27.
 */
@RequiresPresenter(ArticleWritePresenter.class)
public class ArticleWriteActivity extends BaseActivity<ArticleWritePresenter> {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.ed_title)
    EditText edTitle;
    @InjectView(R.id.ed_desc)
    EditText edDesc;
    @InjectView(R.id.ed_content)
    EditText edContent;
    @InjectView(R.id.view_pic)
    SimpleDraweeView pic;
    @InjectView(R.id.btn_send)
    Button btnSend;
    @InjectView(R.id.ripple)
    MaterialRippleLayout ripple;

    private static final int REQUEST_IMAGE = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_write);
        ButterKnife.inject(this);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().addPic();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().sendArticleInfo();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    public String getArticleTitle(){
        return edTitle.getText().toString();
    }

    public String getArticleDesc(){
        return edDesc.getText().toString();
    }

    public String getArticleContent(){
        return edContent.getText().toString();
    }

    public void showPic(Uri uri){
        pic.setImageURI(uri);
    }
        }
