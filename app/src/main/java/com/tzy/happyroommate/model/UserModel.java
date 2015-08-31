package com.tzy.happyroommate.model;


import android.os.Handler;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.jude.beam.model.AbsModel;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.app.APP;
import com.tzy.happyroommate.config.Config;
import com.tzy.happyroommate.model.bean.Attention;
import com.tzy.happyroommate.model.bean.Fans;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.model.callback.DataCallback;
import com.tzy.happyroommate.model.callback.StatusCallback;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by tzy on 2015/8/17.
 */
public class UserModel extends AbsModel {

    public static UserModel getInstance() {
        return getInstance(UserModel.class);
    }

    public void register(final String email, final String name, final String password, final DataCallback<MyUser> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Attention attention = new Attention();
                attention.setUsername(name);//注册的时候传进来的是username,这里还没有本地的缓存对象
                attention.save(APP.getContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        final Fans fans = new Fans();
                        fans.setUsername(name);
                        fans.save(APP.getContext(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                final MyUser user = new MyUser();
                                user.setEmail(email);
                                user.setUsername(name);
                                user.setPassword(password);
                                user.setPic(Config.defaultPic);
                                user.setRoomId(null);
                                user.setAttentionId(attention.getObjectId());//设置关注和粉丝的id
                                user.setFansId(fans.getObjectId());
                                user.signUp(APP.getContext(), new SaveListener() {
                                    @Override
                                    public void onSuccess() {
                                        attention.setUser(user);
                                        MyUser otherUser = new MyUser();
                                        otherUser.setObjectId("f495d0a0f1");
                                        BmobRelation bmobRelation = new BmobRelation();
                                        bmobRelation.add(otherUser);
                                        attention.setAttention(bmobRelation);
                                        attention.update(APP.getContext(), new UpdateListener() {
                                            @Override
                                            public void onSuccess() {
                                                callback.success("", user);
                                            }

                                            @Override
                                            public void onFailure(int i, String s) {
                                                callback.failure(s);
                                            }
                                        });
                                    }//注册成功

                                    @Override
                                    public void onFailure(int i, String s) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }
        }, 1000);
    }

    public void login(final String username, final String password, final DataCallback<MyUser> callback) {
        final MyUser myUser = new MyUser();
        myUser.setUsername(username);
        myUser.setPassword(password);//这三句应该是不需要的
        //通过邮箱登录,这里获取到的是application实例
        myUser.login(APP.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                callback.success("", myUser);
            }

            @Override
            public void onFailure(int i, String s) {
                callback.failure(s);
            }
        });
    }

    //对于id来说只有单条数据
    public void queryUserByObjectId(String objectId, final DataCallback<MyUser> callback) {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.getObject(APP.getContext(), objectId, new GetListener<MyUser>() {
            @Override
            public void onSuccess(MyUser myUser) {
                JUtils.Toast("查询成功");
                callback.success("", myUser);
            }

            @Override
            public void onFailure(int i, String s) {
                JUtils.Toast("查询失败");
                callback.failure(s);
            }
        });


    }

    public void queryUserByEmail(String email, final DataCallback<List<MyUser>> callback) {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("email", email);
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

    //不确定username是否可能会重复
    public void queryUsersByUserName(String username, final DataCallback<List<MyUser>> callback) {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("username", username);
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

    public void queryUsersByNickName(String nickName, final DataCallback<List<MyUser>> callback) {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("nickName", nickName);
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

    public void updataUserInfo(String nickName, Integer age, Boolean sex, String city, String email, String roomName, final StatusCallback callback) {
        MyUser user = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
        if (user.getRoomName().isEmpty()) {
            JUtils.Toast("请创建或加入寝室后再更新");
            callback.failure("");
        } else if (roomName.isEmpty()) {
            JUtils.Toast("寝室名字不能为空");
            callback.failure("");
        } else {
            MyUser newUser = new MyUser();
            newUser.setNickName(nickName);
            newUser.setAge(age);
            newUser.setSex(sex);
            newUser.setCity(city);
            newUser.setRoomName(roomName);
            newUser.setEmail(email);
            updateUser(newUser, callback);
        }
    }

    public void updateUserFace(final String filePath, final StatusCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BTPFileResponse response = BmobProFile.getInstance(APP.getContext()).upload(filePath, new UploadListener() {

                    @Override
                    public void onSuccess(String fileName, String url, BmobFile file) {
                        JUtils.Log("bmob", "文件上传成功：" + fileName + ",可访问的文件地址：" + file.getUrl());
                        // TODO Auto-generated method stub
                        if (callback != null) {
                            saveUserFace(file.getUrl(), fileName, file, callback);
                        }
                    }

                    @Override
                    public void onProgress(int progress) {
                        // TODO Auto-generated method stub
                        JUtils.Log("bmob", "onProgress :" + progress);
                        JUtils.Toast("正在上传" + progress);
                    }

                    @Override
                    public void onError(int statuscode, String errormsg) {
                        // TODO Auto-generated method stub
                        JUtils.Log("bmob", "文件上传失败：" + errormsg);
                        if (callback != null) {
                            callback.failure(errormsg);
                        }
                    }
                });
            }
        }, 1000);
    }

    private void updateUser(final MyUser newUser, final StatusCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BmobUser bmobUser = BmobUser.getCurrentUser(APP.getContext());
                newUser.update(APP.getContext(), bmobUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        if (callback != null)
                            callback.success("");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        if (callback != null)
                            callback.failure(s);
                    }
                });
            }
        }, 1000);
    }

    private void saveUserFace(String url, String filename, BmobFile file, final StatusCallback callback) {
        MyUser newUser = new MyUser();
        newUser.setPic(url);
        newUser.setPicFileName(filename);
        BmobUser bmobUser = BmobUser.getCurrentUser(APP.getContext());
        newUser.update(APP.getContext(), bmobUser.getObjectId(), new UpdateListener() {
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
}
