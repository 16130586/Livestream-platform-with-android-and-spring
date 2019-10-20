package com.t4.LiveServer.model;

import java.util.Date;

public class Stream {
    private Integer streamId;
    private  String wowzaId;
    private  StreamType streamType;
    private  Integer totalView;
    private Date startTime;
    private Date endTime;
    private StreamStatus status;

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

    public StreamType getStreamType() {
        return streamType;
    }

    public void setStreamType(StreamType streamType) {
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

    public StreamStatus getStatus() {
        return status;
    }

    public void setStatus(StreamStatus status) {
        this.status = status;
    }
}
