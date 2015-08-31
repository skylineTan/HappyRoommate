package com.tzy.happyroommate.model;

import android.os.Handler;

import com.jude.beam.model.AbsModel;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.app.APP;
import com.tzy.happyroommate.model.bean.Attention;
import com.tzy.happyroommate.model.bean.Fans;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.model.callback.DataCallback;
import com.tzy.happyroommate.model.callback.StatusCallback;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by dell on 2015/8/21.
 */
public class AttentionModel extends AbsModel {
    public static AttentionModel getInstance() {
        return getInstance(AttentionModel.class);
    }

    public void getAllAttention(final DataCallback<List<MyUser>> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUser currentUser = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                BmobQuery<MyUser> query = new BmobQuery<>();
                Attention attention = new Attention();
                attention.setObjectId(currentUser.getAttentionId());
                query.addWhereRelatedTo("attention", new BmobPointer(attention));
                query.findObjects(APP.getContext(), new FindListener<MyUser>() {
                    @Override
                    public void onSuccess(List<MyUser> list) {
                        callback.success("", list);
                    }

                    @Override
                    public void onError(int i, String s) {
                        JUtils.Toast("没有朋友");
                        callback.failure(s);//全部设置的是failure
                    }
                });
            }
        }, 1000);
    }

    public void getAllFans(final DataCallback<List<MyUser>> callback) {
        MyUser currentUser = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
        BmobQuery<MyUser> query = new BmobQuery<>();
        Fans fans = new Fans();
        fans.setObjectId(currentUser.getFansId());
        query.addWhereRelatedTo("fans", new BmobPointer(fans));
        query.findObjects(APP.getContext(), new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                callback.success("", list);
            }

            @Override
            public void onError(int i, String s) {
                callback.failure(s);
            }
        });
    }

    public void attentionOther(final MyUser otherUser, final StatusCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //关注别人
                MyUser currentUser = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                Attention attention = new Attention();
                attention.setObjectId(currentUser.getAttentionId());
                attention.setUser(currentUser);
                BmobRelation bmobRelation1 = new BmobRelation();
                bmobRelation1.add(otherUser);
                attention.setAttention(bmobRelation1);
                attention.update(APP.getContext(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        callback.success("");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        callback.failure(s);
                    }
                });

                //别人的粉丝有你
                Fans fans = new Fans();
                fans.setObjectId(otherUser.getFansId());
                fans.setUser(otherUser);
                BmobRelation bmobRelation2 = new BmobRelation();
                bmobRelation2.add(currentUser);
                fans.setFans(bmobRelation2);
                fans.update(APP.getContext(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
//不需要返回callback
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }
        }, 1000);

    }

    public void concelAttention(MyUser otherUser, final StatusCallback callback) {
        final MyUser currentUser = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
        BmobRelation bmobRelation = new BmobRelation();
        Attention attention = new Attention();
        attention.setObjectId(currentUser.getAttentionId());
        bmobRelation.remove(otherUser);//移出粉丝
        currentUser.update(APP.getContext(), new UpdateListener() {
            @Override
            public void onSuccess() {
                currentUser.update(APP.getContext(), new UpdateListener() {
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
}
