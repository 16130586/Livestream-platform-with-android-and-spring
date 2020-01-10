package com.t4.LiveServer.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_like")
public class UserLike {
    private Integer id;
    private Integer userId;
    private Integer streamId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "stream_id")
    public Integer getStreamId() {
        return streamId;
    }

    public void setStreamId(Integer streamId) {
        this.streamId = streamId;
    }
}



