package com.t4.androidclient.model.livestream;

public class Notification {
    private String message;
    private LiveStream stream;
    private Integer status;

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
