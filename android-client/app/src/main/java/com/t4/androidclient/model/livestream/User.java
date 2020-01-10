package com.t4.androidclient.model.livestream;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class User {
    @JsonAlias({"userId", "id"})
    public Integer id;
    @JsonAlias({"userName", "username"})
    public String username;
    @JsonAlias({"nickname", "nickName"})
    public String nickname;
    public String gmail;
    public String description;
    public Integer subscribeTotal;
    public String avatar;
    public String background;
    public Integer isActivated;
    public Integer isPublisher;

    public List<Notification> notifications;
    public List<Genre> favouriteType;
    public List<FavouriteSaved> favouriteSaveds;
    public Subscription subscription;
    public List<LiveStream> streams;

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gmail='" + gmail + '\'' +
                ", description='" + description + '\'' +
                ", subscribeTotal=" + subscribeTotal +
                ", avatar='" + avatar + '\'' +
                ", background='" + background + '\'' +
                ", isActivated=" + isActivated +
                ", isPublisher=" + isPublisher +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsPublisher() {
        return isPublisher;
    }

    public void setIsPublisher(Integer isPublisher) {
        this.isPublisher = isPublisher;
    }

    public Integer getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Integer isActivated) {
        this.isActivated = isActivated;
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

    public String getDescription() {
        return description;
    }

    public String getBackground() {
        return background;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBackground(String background) {
        this.background = background;
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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscriptions(Subscription subscription) {
        this.subscription = subscription;
    }

    public List<LiveStream> getStreams() {
        return streams;
    }

    public void setStreams(List<LiveStream> streams) {
        this.streams = streams;
    }

}
