package com.t4.LiveServer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    private Integer userId;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "avatar")
    private byte[] avatar;
    @Column(name = "gmail")
    private String gmail;
    @Column(name = "forgot_token")
    private String forgotToken;
    @Column(name = "subscribe_total")
    private Integer subscribeTotal;
    @ManyToMany
    @JoinTable(name = "notification_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notify_id"))
    private List<Notification> notifications;
    @ManyToMany
    @JoinTable(name = "user_favourite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private List<StreamType> favouriteType;
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<FavoriteSaved> favouriteSaved;
    @ManyToMany
    @JsonIgnoreProperties(value = {"streams", "subscribers", "favouriteType", "notifications", "favouriteSaved"})
    @JoinTable(name = "subscriber",
            joinColumns = @JoinColumn(name = "publisher_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    private List<User> subscribers;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<PaySubscription> paySubscriptions;
    @OneToMany
    @JsonIgnoreProperties("owner")
    @JoinColumn(name = "owner_id")
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

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
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

    public Integer getSubscribeTotal() {
        return subscribeTotal;
    }

    public void setSubscribeTotal(Integer subscribeTotal) {
        this.subscribeTotal = subscribeTotal;
    }

    public List<StreamType> getFavouriteType() {
        return favouriteType;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public List<FavoriteSaved> getFavouriteSaved() {
        return favouriteSaved;
    }

    public void setFavouriteSaved(List<FavoriteSaved> favouriteSaved) {
        this.favouriteSaved = favouriteSaved;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void setFavouriteType(List<StreamType> favouriteType) {
        this.favouriteType = favouriteType;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
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

    public boolean checkExpiredSubscription() {
        if (paySubscriptions == null || paySubscriptions.size() == 0)
            return false;
        Date endTime = this.paySubscriptions.get(paySubscriptions.size()-1).getEndTime();
        if (endTime == null)
            return false;
        return (endTime.compareTo(new Date()) >= 0);
    }
}
