package com.tzy.happyroommate.model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.jude.beam.model.AbsModel;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.app.APP;
import com.tzy.happyroommate.model.bean.Article;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.model.callback.DataCallback;
import com.tzy.happyroommate.model.callback.StatusCallback;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by skyline on 2015/8/28.
 */
public class ArticleModel extends AbsModel {
    private String image = "";

    public static ArticleModel getInstance() {
        return getInstance(ArticleModel.class);
    }

    public void addImage(Uri uri) {
        String data = null;
        if (uri != null) {
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
        } else {
            JUtils.Log("uri为空");
        }
        image = data;
        JUtils.Log("获得头像string");
    }

    public void sendArticle(final String title, final String desc, final String content, final DataCallback<Article> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUser user = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                final Article article = new Article();
                article.setTitle(title);
                article.setDesc(desc);
                article.setContent(content);
                article.setAuthor(user);
                article.setAuthorName(user.getUsername());
                if (!image.isEmpty()) {
                    BTPFileResponse response = BmobProFile.getInstance(APP.getContext()).upload(image, new UploadListener() {
                        @Override
                        public void onSuccess(String s, String s1, BmobFile bmobFile) {
                            article.setPic(bmobFile.getUrl());
                            article.save(APP.getContext(), new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    image = "";
                                    callback.success("", article);
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    callback.failure(s);
                                }
                            });
                        }

                        @Override
                        public void onProgress(int i) {
                            JUtils.Toast(String.valueOf(i));

                        }

                        @Override
                        public void onError(int i, String s) {
                            callback.failure(s);
                        }
                    });
                } else {
                    JUtils.Toast("图片不能为空");
                }
            }
        }, 4000);

    }

    public void getAllArticle(final DataCallback<List<Article>> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BmobQuery<Article> query = new BmobQuery<>();
                query.order("-createdAt");
                query.findObjects(APP.getContext(), new FindListener<Article>() {
                    @Override
                    public void onSuccess(List<Article> list) {
                        callback.success("", list);
                    }

                    @Override
                    public void onError(int i, String s) {
                        callback.failure(s);
                    }
                });
            }
        }, 1000);

    }

    public void deleteArticle(final Article article, final StatusCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUser user = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                if (user.getUsername().equals(article.getAuthorName())) {
                    Article article1 = new Article();
                    article1.setObjectId(article.getObjectId());
                    article1.delete(APP.getContext(), new DeleteListener() {
                        @Override
                        public void onSuccess() {
                            callback.success("");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            callback.failure(s);

                        }
                    });
                } else {
                    JUtils.Toast("没有权限删除文章");
                }
            }
        }, 1000);
    }
}
