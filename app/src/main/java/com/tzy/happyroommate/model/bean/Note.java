package com.tzy.happyroommate.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by skyline on 2015/8/28.
 */
public class Note extends BmobObject{
    private String content;
    private Room author;
    private String authorName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Room getAuthor() {
        return author;
    }

    public void setAuthor(Room author) {
        this.author = author;
    }
}
