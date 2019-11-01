package com.t4.LiveServer.model;

import java.util.Date;

public class Comment {
    private Integer commentId;
    private String message;
    private Date createTime;
    private Integer streamStatus;
    private Integer commentSource;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStreamStatus() {
        return streamStatus;
    }

    public void setStreamStatus(Integer streamStatus) {
        this.streamStatus = streamStatus;
    }

    public Integer getCommentSource() {
        return commentSource;
    }

    public void setCommentSource(Integer commentSource) {
        this.commentSource = commentSource;
    }
}
