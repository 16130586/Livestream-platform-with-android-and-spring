package com.t4.LiveServer.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {

    private Integer commentId;
    private String message;
    private Date createTime;
    private Integer streamStatus;
    private Integer commentSource;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "status")
    public Integer getStreamStatus() {
        return streamStatus;
    }

    public void setStreamStatus(Integer streamStatus) {
        this.streamStatus = streamStatus;
    }

    @Column(name = "source")
    public Integer getCommentSource() {
        return commentSource;
    }

    public void setCommentSource(Integer commentSource) {
        this.commentSource = commentSource;
    }
}
