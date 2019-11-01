package com.t4.LiveServer.model;

import java.util.Base64;
import java.util.List;

public class User {
    private Integer userId;
    private String userName;
    private String password;
    private String nickName;
    private Base64 avatar;
    private String gmail;
    private String forgotToken;
    private Integer subcriberTotal;
    private List<Notification> notifications;
    private List<StreamType> favouriteType;
    private List<FavoriteSaved> favoriteSaveds;
    private List<User> subcribers;
    private List<PaySubscription> paySubscriptions;
    private List<Stream> streams;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Base64 getAvatar() {
        return avatar;
    }

    public void setAvatar(Base64 avatar) {
        this.avatar = avatar;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getForgotToken() {
        return forgotToken;
    }

    public void setForgotToken(String forgotToken) {
        this.forgotToken = forgotToken;
    }

    public Integer getSubcriberTotal() {
        return subcriberTotal;
    }

    public void setSubcriberTotal(Integer subcriberTotal) {
        this.subcriberTotal = subcriberTotal;
    }

    public List<StreamType> getFavouriteType() {
        return favouriteType;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void setFavouriteType(List<StreamType> favouriteType) {
        this.favouriteType = favouriteType;
    }

    public List<FavoriteSaved> getFavoriteSaveds() {
        return favoriteSaveds;
    }

    public void setFavoriteSaveds(List<FavoriteSaved> favoriteSaveds) {
        this.favoriteSaveds = favoriteSaveds;
    }

    public List<User> getSubcribers() {
        return subcribers;
    }

    public void setSubcribers(List<User> subcribers) {
        this.subcribers = subcribers;
    }

    public List<PaySubscription> getPaySubscriptions() {
        return paySubscriptions;
    }

    public void setPaySubscriptions(List<PaySubscription> paySubscriptions) {
        this.paySubscriptions = paySubscriptions;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }
}
