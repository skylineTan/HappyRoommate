package com.tzy.happyroommate.model.bean;

import com.bmob.BmobProFile;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by tzy on 2015/8/11.
 */
public class Trend extends BmobObject {
    private Integer type;//type指的是动态的类型
    private String content;
    private Room author;
    private String trendPic;
    private String[] images;//帖子图片,直接用的是新的图片类
    //private String[] imagesName;
    private BmobRelation likes;//多对多关系：用于存储喜欢该帖子的所有用户，点赞
    private String roomName;
    private Integer praiseCount;
    private Integer commentCount;



    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Room getAuthor() {
        return author;
    }

    public void setAuthor(Room author) {
        this.author = author;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public String getTrendPic() {
        return trendPic;
    }

    public void setTrendPic(String trendPic) {
        this.trendPic = trendPic;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

}
