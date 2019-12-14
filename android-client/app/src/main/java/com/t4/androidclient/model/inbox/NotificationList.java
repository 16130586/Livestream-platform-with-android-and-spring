package com.t4.androidclient.model.inbox;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.t4.androidclient.model.livestream.Notification;

import java.util.List;

public class NotificationList {

    @JsonAlias({"data"})
    private List<Notification> data;

    public NotificationList() {}

    public List<Notification> getData() {
        return data;
    }

    public void setData(List<Notification> data) {
        this.data = data;
    }
}
