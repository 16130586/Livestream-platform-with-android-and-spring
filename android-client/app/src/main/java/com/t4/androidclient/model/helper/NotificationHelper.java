package com.t4.androidclient.model.helper;

import android.widget.ArrayAdapter;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t4.androidclient.model.inbox.NotificationList;
import com.t4.androidclient.model.livestream.Notification;
import com.t4.androidclient.model.livestream.page.FacebookPage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NotificationHelper {

    public NotificationHelper() {

    }

    public List<Notification> parseNotification(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            NotificationList listNotification = objectMapper.readValue(json, NotificationList.class);
            return listNotification.getData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
