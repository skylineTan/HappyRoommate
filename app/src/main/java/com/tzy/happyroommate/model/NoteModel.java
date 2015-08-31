package com.tzy.happyroommate.model;

import android.os.Handler;

import com.jude.beam.model.AbsModel;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.app.APP;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.model.bean.Note;
import com.tzy.happyroommate.model.bean.Room;
import com.tzy.happyroommate.model.callback.DataCallback;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by skyline on 2015/8/28.
 */
public class NoteModel extends AbsModel {

    public static NoteModel getInstance() {
        return getInstance(NoteModel.class);
    }

    public void sendNote(final String content, final DataCallback<Note> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!content.isEmpty()) {
                    MyUser user = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
                    if (user.getRoomId() != null) {
                        Room room = new Room();
                        room.setObjectId(user.getRoomId());
                        final Note note = new Note();
                        note.setAuthor(room);
                        note.setContent(content);
                        note.setAuthorName(user.getUsername());
                        note.save(APP.getContext(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                callback.success("", note);
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                callback.failure(s);
                            }
                        });
                    } else {
                        JUtils.Toast("您还没有加入寝室，不能发布便利贴");
                    }
                } else {
                    JUtils.Toast("输入内容不能为空");
                }
            }
        }, 1000);
    }

    public void getAllNote(final DataCallback<List<Note>> callback) {
        MyUser user = BmobUser.getCurrentUser(APP.getContext(), MyUser.class);
        BmobQuery<Note> query = new BmobQuery<Note>();
        Room room = new Room();
        room.setObjectId(user.getRoomId());
        query.addWhereEqualTo("author", room);
        query.findObjects(APP.getContext(), new FindListener<Note>() {
            @Override
            public void onSuccess(List<Note> list) {
                JUtils.Log("note" + list.size());
                callback.success("", list);
            }

            @Override
            public void onError(int i, String s) {
                JUtils.Log("没找到");
                callback.failure(s);
            }
        });
    }
}

