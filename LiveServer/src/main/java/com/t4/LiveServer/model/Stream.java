package com.t4.LiveServer.model;

import java.util.Date;
import java.util.List;

public class Stream {
    private Integer streamId;
    private String wowzaId;
    private List<StreamType> streamType;
    private User owner;
    private Integer totalView;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private String forwards;
    private String forwardsUrl;
    private String storedUrl;
    private List<Comment> comments;

    public Integer getStreamId() {
        return streamId;
    }

    public void setStreamId(Integer streamId) {
        this.streamId = streamId;
    }

    public String getWowzaId() {
        return wowzaId;
    }

    public void setWowzaId(String wowzaId) {
        this.wowzaId = wowzaId;
    }

    public List<StreamType> getStreamType() {
        return streamType;
    }

    public void setStreamType(List<StreamType> streamType) {
        this.streamType = streamType;
    }

    public Integer getTotalView() {
        return totalView;
    }

    public void setTotalView(Integer totalView) {
        this.totalView = totalView;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getForwards() {
        return forwards;
    }

    public void setForwards(String forwards) {
        this.forwards = forwards;
    }

    public String getForwardsUrl() {
        return forwardsUrl;
    }

    public void setForwardsUrl(String forwardsUrl) {
        this.forwardsUrl = forwardsUrl;
    }

    public String getStoredUrl() {
        return storedUrl;
    }

    public void setStoredUrl(String storedUrl) {
        this.storedUrl = storedUrl;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
