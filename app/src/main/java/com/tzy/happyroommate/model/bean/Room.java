package com.tzy.happyroommate.model.bean;

import android.provider.ContactsContract;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by tzy on 2015/8/19.
 */
public class Room extends BmobObject{
    private String roomName;
    private MyUser roomCreator;
    private String creatorName;
    private BmobRelation roommate;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public MyUser getRoomCreator() {
        return roomCreator;
    }

    public void setRoomCreator(MyUser roomCreator) {
        this.roomCreator = roomCreator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public BmobRelation getRoommate() {
        return roommate;
    }

    public void setRoommate(BmobRelation roommate) {
        this.roommate = roommate;
    }

}
