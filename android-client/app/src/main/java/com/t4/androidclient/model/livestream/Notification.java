package com.t4.androidclient.model.livestream;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Notification {
    @JsonAlias({"notificationId"})
    private long id;
    private String message;
    private LiveStream stream;
    private Integer status;

    public Notification() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LiveStream getStream() {
        return stream;
    }

    public void setStream(LiveStream stream) {
        this.stream = stream;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
