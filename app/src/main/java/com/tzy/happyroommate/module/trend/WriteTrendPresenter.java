package com.tzy.happyroommate.module.trend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jude.beam.nucleus.manager.Presenter;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.model.TrendModel;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.model.callback.DataCallback;

import java.util.List;


/**
 * Created by tzy on 2015/8/12.
 */
public class WriteTrendPresenter extends Presenter<WriteTrendActivity> {
    private ImageProvider mImageProvider;
    TrendAdapter trendAdapter;
    TrendFragment trendFragment;
    TrendPresenter trendPresenter;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        mImageProvider = new ImageProvider(getView());
    }


    public void changeFace(int way) {
        OnImageSelectListener onImageSelectListener = new OnImageSelectListener() {
            @Override
            public void onImageSelect() {
                getView().showProgress("正在加载");
            }

            @Override
            public void onImageLoaded(Uri uri) {
                getView().dismissProgress();
                mImageProvider.corpImage(uri, 300, 300, new OnImageSelectListener() {
                    @Override
                    public void onImageSelect() {
                    }

                    @Override
                    public void onImageLoaded(Uri uri) {
                        getView().addImageAddedView(uri);
                        JUtils.Log("获得头像uri");
                        TrendModel.getInstance().addImages(uri);
                    }

                    @Override
                    public void onError() {
                    }
                });
            }

            @Override
            public void onError() {
                getView().dismissProgress();
            }
        };
        switch (way){
            case 0:
                mImageProvider.getImageFromCamera(onImageSelectListener);
                break;
            case 1:
                mImageProvider.getImageFromAlbum(onImageSelectListener);
                break;
            case 2:
                mImageProvider.getImageFromNet(onImageSelectListener);
        }
    }

    public void sendTrendInfo(){
        TrendModel.getInstance().sendTrend(getView().getTrendContent(),new DataCallback<Trend>() {
            @Override
            public void success(String info, Trend data) {
                JUtils.Toast("发布成功");
                        trendPresenter = new TrendPresenter();
                        getView().finish();
                        trendPresenter.refresh();
                    }

                    @Override
                    public void failure(String info) {
                        super.failure(info);
                        JUtils.Toast("发布失败");

                    }
                });

            }


    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        mImageProvider.onActivityResult(requestCode,resultCode,data);
    }
}
