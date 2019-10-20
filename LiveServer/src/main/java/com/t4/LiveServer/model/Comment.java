package com.t4.LiveServer.model;

import java.util.Date;

public class Comment {
    private Integer commentId;
    private String message;
    private Date createTime;
    private StreamStatus streamStatus;
    private CommentSource commentSource;

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

    public StreamStatus getStreamStatus() {
        return streamStatus;
    }

    public void setStreamStatus(StreamStatus streamStatus) {
        this.streamStatus = streamStatus;
    }

    public CommentSource getCommentSource() {
        return commentSource;
    }

    public void setCommentSource(CommentSource commentSource) {
        this.commentSource = commentSource;
    }
}
