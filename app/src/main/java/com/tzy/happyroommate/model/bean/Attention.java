package com.tzy.happyroommate.model.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by tzy on 2015/8/19.
 */
public class Attention extends BmobObject{
    private BmobRelation attention;
    private MyUser user;
    private String username;

    public BmobRelation getAttention() {
        return attention;
    }

    public void setAttention(BmobRelation attention) {
        this.attention = attention;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
