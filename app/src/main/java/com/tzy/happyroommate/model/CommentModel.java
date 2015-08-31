package com.tzy.happyroommate.model;

import android.os.Handler;

import com.jude.beam.model.AbsModel;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.app.APP;
import com.tzy.happyroommate.model.bean.Comment;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.model.callback.DataCallback;
import com.tzy.happyroommate.model.callback.StatusCallback;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by tzy on 2015/8/17.
 */
public class CommentModel extends AbsModel {

    public static CommentModel getInstance() {
        return getInstance(CommentModel.class);
    }

    public void sendComment(final String commentContent, final Trend trend, final StatusCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUser user = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                final Comment comment = new Comment();
                comment.setContent(commentContent);
                comment.setAuthor(user);
                comment.setTrend(trend);
                comment.setAuthorName(user.getUsername());
                comment.setCommentPic(user.getPic());
                comment.save(APP.getContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        trend.setCommentCount(trend.getCommentCount() + 1);
                        trend.update(APP.getContext(), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                callback.success("");
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                callback.failure(s);
                            }
                        });
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        callback.failure(s);
                    }
                });
            }
        }, 1000);
    }

    public void getAllComment(final Trend data, final DataCallback<List<Comment>> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BmobQuery<Comment> query = new BmobQuery<>();
                query.addWhereEqualTo("trend", new BmobPointer(data));
                query.order("-createdAt");
                query.findObjects(APP.getContext(), new FindListener<Comment>() {
                    @Override
                    public void onSuccess(List<Comment> list) {
                        JUtils.Log("评论数" + String.valueOf(list.size()));
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
}
