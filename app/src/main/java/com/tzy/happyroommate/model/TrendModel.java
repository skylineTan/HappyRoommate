package com.tzy.happyroommate.model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadBatchListener;
import com.bmob.btp.callback.UploadListener;
import com.jude.beam.model.AbsModel;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.app.APP;
import com.tzy.happyroommate.model.bean.Attention;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.model.bean.Room;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.model.callback.DataCallback;
import com.tzy.happyroommate.model.callback.StatusCallback;
import com.tzy.happyroommate.utils.AddedView.PieceViewGroup;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by tzy on 2015/8/12.
 */
public class TrendModel extends AbsModel {
    private ArrayList<String> images = new ArrayList<>();

    public static TrendModel getInstance() {
        return getInstance(TrendModel.class);
    }

    public void getUserTrend(final DataCallback<List<Trend>> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUser currentUser = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                BmobQuery<Trend> query = new BmobQuery<>();
                Room room = new Room();
                room.setObjectId(currentUser.getRoomId());
                query.addWhereEqualTo("author", new BmobPointer(room));
                query.order("-createdAt");
                query.findObjects(APP.getContext(), new FindListener<Trend>() {
                    @Override
                    public void onSuccess(List<Trend> list) {
                        JUtils.Log("自己动态的数目" + list.size());
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

    public void getFriendsTrend(final DataCallback<List<Trend>> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUser currentUser = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                final BmobQuery<MyUser> query = new BmobQuery<>();
                Attention attention = new Attention();
                attention.setObjectId(currentUser.getAttentionId());
                query.addWhereRelatedTo("attention", new BmobPointer(attention));
                query.addWhereNotEqualTo("roomId", "");
                query.findObjects(APP.getContext(), new FindListener<MyUser>() {
                    @Override
                    public void onSuccess(List<MyUser> list) {
                        JUtils.Log("得到有寝室id好友数目" + list.size());
                        BmobQuery<Room> innerQuery = new BmobQuery<>();
                        String[] roomIds = new String[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            roomIds[i] = list.get(i).getRoomId();
                        }
                        JUtils.Log("得到有寝室id数目" + roomIds.length);
                        innerQuery.addWhereContainedIn("objectId", Arrays.asList(roomIds));
                        BmobQuery<Trend> query = new BmobQuery<Trend>();
                        query.order("-createdAt");
                        query.addWhereMatchesQuery("author", "Room", innerQuery);
                        query.findObjects(APP.getContext(), new FindListener<Trend>() {
                            @Override
                            public void onSuccess(List<Trend> list) {
                                callback.success("", list);
                            }

                            @Override
                            public void onError(int i, String s) {
                                callback.failure(s);
                            }
                        });
                    }

                    @Override
                    public void onError(int i, String s) {
                        callback.failure(s);
                    }
                });
            }
        }, 1000);
    }

    public void getFriendTrend(final MyUser user, final DataCallback<List<Trend>> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Room room = new Room();
                room.setObjectId(user.getRoomId());
                BmobQuery<Trend> query = new BmobQuery<>();
                query.addWhereEqualTo("author", new BmobPointer(room));
                query.findObjects(APP.getContext(), new FindListener<Trend>() {
                    @Override
                    public void onSuccess(List<Trend> list) {
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

    public void getAllTrend(final DataCallback<List<Trend>> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final MyUser currentUser = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                final BmobQuery<MyUser> query = new BmobQuery<>();
                Attention attention = new Attention();
                attention.setObjectId(currentUser.getAttentionId());
                query.addWhereRelatedTo("attention", new BmobPointer(attention));
                query.addWhereNotEqualTo("roomId", "");
                query.findObjects(APP.getContext(), new FindListener<MyUser>() {
                    @Override
                    public void onSuccess(List<MyUser> list) {
                        JUtils.Log("得到有寝室id好友数目" + list.size());
                        BmobQuery<Room> innerQuery = new BmobQuery<>();
                        String[] roomIds = new String[list.size() + 1];
                        for (int i = 0; i < list.size(); i++) {
                            roomIds[i] = list.get(i).getRoomId();
                        }
                        roomIds[list.size()] = currentUser.getRoomId();
                        JUtils.Log("得到有寝室id数目" + roomIds.length);
                        innerQuery.addWhereContainedIn("objectId", Arrays.asList(roomIds));
                        BmobQuery<Trend> query = new BmobQuery<Trend>();
                        query.order("-createdAt");
                        query.addWhereMatchesQuery("author", "Room", innerQuery);
                        query.findObjects(APP.getContext(), new FindListener<Trend>() {
                            @Override
                            public void onSuccess(List<Trend> list) {
                                callback.success("", list);
                            }

                            @Override
                            public void onError(int i, String s) {
                                callback.failure(s);
                            }
                        });
                    }

                    @Override
                    public void onError(int i, String s) {
                        callback.failure(s);
                    }
                });
            }
        }, 1000);

    }


    public void sendTrend(final String content, final DataCallback<Trend> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUser user = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                if (user.getRoomId() != null) {
                    if (!content.isEmpty()) {
                        Room room = new Room();
                        room.setObjectId(user.getRoomId());
                        final Trend trend = new Trend();
                        trend.setAuthor(room);
                        trend.setContent(content);
                        trend.setTrendPic(user.getPic());
                        trend.setRoomName(user.getRoomName());
                        trend.setPraiseCount(0);
                        trend.setCommentCount(0);
                        if (images.size() != 0) {
                            String strings[] = new String[images.size()];
                            for (int i = 0, j = images.size(); i < j; i++) {
                                strings[i] = images.get(i);
                            }
                            BmobProFile.getInstance(APP.getContext()).uploadBatch(strings, new UploadBatchListener() {
                                @Override
                                public void onSuccess(boolean b, String[] strings, String[] strings1, BmobFile[] bmobFiles) {
                                    if (b == true) {
                                        String bmobImages[] = new String[bmobFiles.length];
                                        for (int i = 0; i < bmobFiles.length; i++) {
                                            bmobImages[i] = bmobFiles[i].getUrl();//得到url数组
                                            JUtils.Toast(bmobFiles[i].getUrl());
                                        }
                                        trend.setImages(bmobImages);
                                        trend.save(APP.getContext(), new SaveListener() {
                                            @Override
                                            public void onSuccess() {
                                                images.clear();
                                                callback.success("", trend);
                                            }

                                            @Override
                                            public void onFailure(int i, String s) {
                                                callback.failure(s);
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onProgress(int i, int i1, int i2, int i3) {
                                    JUtils.Toast("onProgress :" + i + "---" + i1 + "---" + i2 + "----" + i3);
                                }

                                @Override
                                public void onError(int i, String s) {
                                    JUtils.Toast("您的操作太过频繁，请稍候再试哦");
                                    callback.failure(s);
                                }
                            });
                        } else {
                            trend.setImages(null);
                            images.clear();
                            trend.save(APP.getContext(), new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    callback.success("", trend);
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    callback.failure(s);
                                }
                            });
                        }
                    } else {
                        JUtils.Toast("输入内容不能为空");
                    }
                } else {
                    JUtils.Toast("您还没有创建或加入寝室，不能发表动态");
                }
            }
        }, 1000);
    }

    public void addImages(Uri uri) {
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
        images.add(data);
        JUtils.Log("获得头像string");
    }

    public void sendPraise(final Trend data, final StatusCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final MyUser user = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                BmobQuery<MyUser> query = new BmobQuery<>();
                query.addWhereRelatedTo("likes", new BmobPointer(data));
                query.findObjects(APP.getContext(), new FindListener<MyUser>() {
                    @Override
                    public void onSuccess(List<MyUser> list) {
                        boolean flag = true;
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getObjectId().equals(user.getObjectId())) {
                                JUtils.Log("关注了");
                                JUtils.Log("匹配好友数" + String.valueOf(list.size()));
                                BmobRelation relation = new BmobRelation();
                                relation.remove(user);
                                data.setLikes(relation);
                                data.setPraiseCount(data.getPraiseCount() - 1);
                                data.update(APP.getContext(), new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        callback.success("concel");//用户取消喜欢帖子
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        callback.failure(s);
                                    }
                                });
                                flag = false;
                            }
                        }
                        if (flag) {
                            JUtils.Log("没关注");
                            BmobRelation bmobRelation = new BmobRelation();
                            bmobRelation.add(user);
                            data.setLikes(bmobRelation);
                            data.setPraiseCount(data.getPraiseCount() + 1);
                            data.update(APP.getContext(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    JUtils.Log("成功关注");
                                    callback.success("like");
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    callback.failure(s);
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                    }
                });
            }
        }, 1000);
    }

    public void concelPraise(final Trend data, final StatusCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUser user = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                BmobRelation bmobRelation = new BmobRelation();
                bmobRelation.remove(user);
                data.setLikes(bmobRelation);
                data.update(APP.getContext(), new UpdateListener() {
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
        }, 1000);

    }

    public void deleteTrend(final Trend trend, final StatusCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUser user = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                if (!user.getRoomName().isEmpty()) {
                    if (user.getRoomName().equals(trend.getRoomName())) {
                        Trend trend1 = new Trend();
                        trend1.setObjectId(trend.getObjectId());
                        trend1.delete(APP.getContext(), new DeleteListener() {
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
                        JUtils.Toast("没有权限删除此条动态");
                    }
                } else {
                    JUtils.Toast("您还没有点击右上角创建寝室");
                }
            }
        }, 1000);
    }
}

