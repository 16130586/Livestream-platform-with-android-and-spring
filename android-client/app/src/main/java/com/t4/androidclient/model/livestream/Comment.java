package com.t4.androidclient.model.livestream;

import java.util.Date;

public class Comment {
    private Integer commentId;
    private String message;
    private Date createTime;
    private Integer streamId;
    private Integer streamStatus;
    private Integer commentSource;
    private Integer videoTime;
    private String ownerName;

    public Comment() {

    }

    public Comment(String ownerName, String message) {
        this.ownerName = ownerName;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", message='" + message + '\'' +
                ", createTime=" + createTime +
                ", streamId=" + streamId +
                ", streamStatus=" + streamStatus +
                ", commentSource=" + commentSource +
                ", videoTime=" + videoTime +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getStreamId() {
        return streamId;
    }

    public void setStreamId(Integer streamId) {
        this.streamId = streamId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public Integer getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(Integer videoTime) {
        this.videoTime = videoTime;
    }
}
