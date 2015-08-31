package com.tzy.happyroommate.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by tzy on 2015/8/17.
 */
public class Comment extends BmobObject {
    private String content;//评论内容
    private MyUser author;//评论的用户
    private Trend trend; //所评论的帖子
    private String commentPic;
    private String authorName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public Trend getTrend() {
        return trend;
    }

    public void setTrend(Trend trend) {
        this.trend = trend;
    }

    public String getCommentPic() {
        return commentPic;
    }

    public void setCommentPic(String commentPic) {
        this.commentPic = commentPic;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
