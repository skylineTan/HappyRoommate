package com.tzy.happyroommate.module.main;



import android.content.ContentResolver;
import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.jude.beam.nucleus.manager.Presenter;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.app.APP;
import com.tzy.happyroommate.model.RoomModel;
import com.tzy.happyroommate.model.UserModel;
import com.tzy.happyroommate.model.callback.StatusCallback;
import com.tzy.happyroommate.module.article.ArticleFragment;
import com.tzy.happyroommate.module.contact.ContactFragment;
import com.tzy.happyroommate.module.note.NoteFragment;
import com.tzy.happyroommate.module.trend.TrendFragment;

/**
 * Created by tzy on 2015/8/13.
 */
public class MainPresenter extends Presenter<MainActivity> {
    private Fragment mChatFragment;//这里实际上每一个都是fragment,里面的布局文件不同。
    private Fragment mContactFragment;
    private Fragment mTrendFragment;
    private Fragment mNoteFragment;
    private ImageProvider mImageProvider;

    @Override
    protected void onCreateView(MainActivity view) {
        super.onCreateView(view);
        showChatFragment();
        mImageProvider = new ImageProvider(getView());
    }

    public void showChatFragment() {
        if (mChatFragment == null) {
            mChatFragment = new ArticleFragment();
        }
        getView().showFragment(mChatFragment);
    }

    public void showContactFragment() {
        if (mContactFragment == null) {
            mContactFragment = new ContactFragment();
        }
        getView().showFragment(mContactFragment);
    }

    public void showTrendFragment() {
        if (mTrendFragment == null) {
            mTrendFragment = new TrendFragment();
        }
        getView().showFragment(mTrendFragment);
    }

    public void showNoteFragment() {
        if (mNoteFragment == null) {
            mNoteFragment = new NoteFragment();
        }
        getView().showFragment(mNoteFragment);
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
                        getView().addImageView(uri);
                        updateUserFaceinfo(uri);
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

    public void updateFansInfo(){

    }

    public void updateRoomNameInfo(String name){
        getView().showProgress("正在更新");
        RoomModel.getInstance().updateRoomName(name, new StatusCallback() {
            @Override
            public void success(String info) {
                getView().dismissProgress();

            }

            @Override
            public void failure(String info) {
                super.failure(info);
                getView().dismissProgress();
            }
        });
    }

    public void createRoom(String roomName){
        getView().showProgress("创建中");
        RoomModel.getInstance().createRoom(roomName, new StatusCallback() {
            @Override
            public void success(String info) {
                getView().dismissProgress();
                JUtils.Toast("创建成功");

            }

            @Override
            public void failure(String info) {
                super.failure(info);
                getView().dismissProgress();
                JUtils.Toast("创建失败");
            }
        });

    }


        @Override
        protected void onResult ( int requestCode, int resultCode, Intent data){
            super.onResult(requestCode, resultCode, data);
            mImageProvider.onActivityResult(requestCode, resultCode, data);
        }
}
