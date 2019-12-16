package com.t4.LiveServer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    private Integer userId;
    private String wowzaId;
    private String userName;
    private String password;
    private String nickName;
    private String avatar;
    private String gmail;
    private String forgotToken;
    private Integer subscribeTotal;
    private List<Notification> notifications;
    private List<StreamType> favouriteType;
    private List<FavoriteSaved> favouriteSaved;
    @JsonIgnoreProperties(value = {"streams", "subscribers", "favouriteType", "notifications", "favouriteSaved"})
    private List<User> subscribers;
    private List<PaySubscription> paySubscriptions;
    @JsonIgnoreProperties(value = {"owner", "comments"})
    private List<Stream> streams;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "wowza_id")
    public String getWowzaId() {
        return wowzaId;
    }

    public void setWowzaId(String wowzaId) {
        this.wowzaId = wowzaId;
    }

    @Column(name = "username")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "nick_name")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Column(name = "gmail")
    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    @Column(name = "forgot_token")
    public String getForgotToken() {
        return forgotToken;
    }

    public void setForgotToken(String forgotToken) {
        this.forgotToken = forgotToken;
    }

    @Column(name = "subscribe_total")
    public Integer getSubscribeTotal() {
        return subscribeTotal != null ? subscribeTotal : 0;
    }

    public void setSubscribeTotal(Integer subscribeTotal) {
        this.subscribeTotal = subscribeTotal;
    }

    @ManyToMany
    @JoinTable(name = "user_favourite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    public List<StreamType> getFavouriteType() {
        return favouriteType;
    }

    @ManyToMany
    @JoinTable(name = "notification_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notify_id"))
    public List<Notification> getNotifications() {
        return notifications;
    }

    @OneToMany
    @JoinColumn(name = "user_id")
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

    @ManyToMany
    @JoinTable(name = "subscriber",
            joinColumns = @JoinColumn(name = "publisher_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    public List<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public List<PaySubscription> getPaySubscriptions() {
        return paySubscriptions;
    }

    public void setPaySubscriptions(List<PaySubscription> paySubscriptions) {
        this.paySubscriptions = paySubscriptions;
    }

    @OneToMany
    @JoinColumn(name = "owner_id")
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

    public void addPaySubscription(PaySubscription paySubscription) {
        paySubscriptions.add(paySubscription);
    }
}
