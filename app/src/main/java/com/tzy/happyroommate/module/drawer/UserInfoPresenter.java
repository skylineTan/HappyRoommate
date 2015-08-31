package com.tzy.happyroommate.module.drawer;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.jude.beam.nucleus.manager.Presenter;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.app.APP;
import com.tzy.happyroommate.model.UserModel;
import com.tzy.happyroommate.model.callback.StatusCallback;

/**
 * Created by dell on 2015/8/21.
 */
public class UserInfoPresenter extends Presenter<UserInfoActivity>{
    private ImageProvider mImageProvider;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        mImageProvider = new ImageProvider(getView());
    }

    public void updataUserInfo(){
        getView().showProgress("更新中");
        UserModel.getInstance().updataUserInfo(getView().getNickName(), getView().getAge(), getView().getSex(), getView().getCity(), getView().getEmail(), getView().getRoomName(), new StatusCallback() {
            @Override
            public void success(String info) {
                getView().dismissProgress();
                JUtils.Toast("更新成功");
            }

            @Override
            public void failure(String info) {
                super.failure(info);
                getView().dismissProgress();
                JUtils.Toast("更新失败");
            }
        });

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
                        //getView().addImageView(uri);
                        updateUserFaceinfo(uri);
                        getView().showFace(uri);
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
        switch (way) {
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

    private void updateUserFaceinfo(Uri uri) {
        getView().showProgress("正在更新头像");
        String data = null;
        if (uri!=null ) {
            final String scheme = uri.getScheme();
            if (scheme == null)
                data = uri.getPath();
            else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
                data = uri.getPath();
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                Cursor cursor = APP.getContext().getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        if (index > -1) {
                            data = cursor.getString(index);
                        }
                    }
                    cursor.close();
                }
            }
        }else {
            JUtils.Log("uri为空");
        }
        JUtils.Log(data);
        UserModel.getInstance().updateUserFace(data, new StatusCallback() {
            @Override
            public void success(String info) {
                JUtils.Toast("更新头像成功");
                getView().dismissProgress();
            }

            @Override
            public void failure(String info) {
                super.failure(info);
                JUtils.Toast("更新头像失败");
                getView().dismissProgress();
            }
        });
    }

    @Override
    protected void onResult ( int requestCode, int resultCode, Intent data){
        super.onResult(requestCode, resultCode, data);
        mImageProvider.onActivityResult(requestCode, resultCode, data);
    }
}
