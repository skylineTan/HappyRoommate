package com.tzy.happyroommate.module.article;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jude.beam.nucleus.manager.Presenter;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.model.ArticleModel;
import com.tzy.happyroommate.model.bean.Article;
import com.tzy.happyroommate.model.callback.DataCallback;

/**
 * Created by skyline on 2015/8/27.
 */
public class ArticleWritePresenter extends Presenter<ArticleWriteActivity>{
    private ArticlePresenter articlePresenter;
    private ImageProvider mImageProvider;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        mImageProvider = new ImageProvider(getView());
    }

    public void sendArticleInfo(){
        ArticleModel.getInstance().sendArticle(getView().getArticleTitle(), getView().getArticleDesc(), getView().getArticleContent(), new DataCallback<Article>() {
            @Override
            public void success(String info, Article data) {
                articlePresenter = new ArticlePresenter();
                getView().finish();
                articlePresenter.refresh();
            }
        });
    }

    public void addPic(){
        OnImageSelectListener onImageSelectListener = new OnImageSelectListener() {
            @Override
            public void onImageSelect() {

            }

            @Override
            public void onImageLoaded(Uri uri) {
                mImageProvider.corpImage(uri, 300, 300, new OnImageSelectListener() {
                    @Override
                    public void onImageSelect() {
                    }

                    @Override
                    public void onImageLoaded(Uri uri) {
                        getView().showPic(uri);
                        JUtils.Log("获得头像uri");
                        ArticleModel.getInstance().addImage(uri);
                    }

                    @Override
                    public void onError() {
                    }
                });
            }

            @Override
            public void onError() {

            }
        };
        mImageProvider.getImageFromAlbum(onImageSelectListener);

    }
    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        mImageProvider.onActivityResult(requestCode, resultCode, data);
    }
}

