package com.t4.androidclient.model.livestream;

import java.util.List;

public class User {
    public Integer id;
    public String username;
    public String nickname;
    public String gmail;
    public String avatar;
    public Integer subscribeTotal;
    public List<Notification> notifications;
    public List<Genre> favouriteType;
    public List<FavouriteSaved> favouriteSaveds;
    public List<Subscription> subscriptions;
    public List<LiveStream> streams;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSubscribeTotal() {
        return subscribeTotal;
    }

    public void setSubscribeTotal(Integer subscribeTotal) {
        this.subscribeTotal = subscribeTotal;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Genre> getFavouriteType() {
        return favouriteType;
    }

    public void setFavouriteType(List<Genre> favouriteType) {
        this.favouriteType = favouriteType;
    }

    public List<FavouriteSaved> getFavouriteSaveds() {
        return favouriteSaveds;
    }

    public void setFavouriteSaveds(List<FavouriteSaved> favouriteSaveds) {
        this.favouriteSaveds = favouriteSaveds;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<LiveStream> getStreams() {
        return streams;
    }

    public void setStreams(List<LiveStream> streams) {
        this.streams = streams;
    }
}
