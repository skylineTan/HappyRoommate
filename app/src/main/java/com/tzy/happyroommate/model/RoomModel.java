package com.tzy.happyroommate.model;

import android.os.Handler;

import com.jude.beam.model.AbsModel;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.app.APP;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.model.bean.Note;
import com.tzy.happyroommate.model.bean.Room;
import com.tzy.happyroommate.model.callback.DataCallback;
import com.tzy.happyroommate.model.callback.StatusCallback;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by tzy on 2015/8/23.
 */
public class RoomModel extends AbsModel {
    public static RoomModel getInstance() {
        return getInstance(RoomModel.class);
    }

    public void createRoom(final String roomName, final StatusCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (roomName.isEmpty()) {
                    JUtils.Toast("寝室名字不能为空");
                    callback.failure("");
                } else {
                    final MyUser currentUser = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                    final Room room = new Room();
                    room.setRoomName(roomName);
                    room.setRoomCreator(currentUser);
                    BmobRelation bmobRelation = new BmobRelation();
                    bmobRelation.add(currentUser);
                    room.setRoommate(bmobRelation);
                    room.save(APP.getContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            currentUser.setRoomId(room.getObjectId());
                            currentUser.setRoomName(roomName);
                            currentUser.update(APP.getContext(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    NoteModel.getInstance().sendNote("您在这里可以发布只有寝室内部可见的便利贴", new DataCallback<Note>() {
                                        @Override
                                        public void success(String info, Note data) {
                                            callback.success("");
                                        }
                                    });
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
        }, 1000);
    }

    public void queryRoomById(final String id, final DataCallback<Room> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BmobQuery<Room> query = new BmobQuery<>();
                query.getObject(APP.getContext(), id, new GetListener<Room>() {
                    @Override
                    public void onSuccess(Room room) {
                        callback.success("", room);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        callback.failure(s);
                    }
                });
            }
        }, 1000);
    }

    public void addRoom(final String id, final StatusCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final MyUser currentUser = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                final Room room = new Room();
                room.setObjectId(id);
                BmobRelation bmobRelation = new BmobRelation();
                bmobRelation.add(currentUser);
                room.setRoommate(bmobRelation);
                room.update(APP.getContext(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        currentUser.setRoomId(room.getObjectId());
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
        }, 1000);
    }

    public void updateRoomName(final String name, final StatusCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (name.isEmpty()) {
                    JUtils.Toast("寝室名字不能为空");
                    callback.failure("");
                } else {
                    final MyUser currentUser = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                    Room room = new Room();
                    room.setObjectId(currentUser.getRoomId());
                    room.setRoomName(name);
                    room.update(APP.getContext(), currentUser.getRoomId(), new UpdateListener() {
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
        }, 1000);
    }
}
