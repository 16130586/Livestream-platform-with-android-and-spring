package com.t4.LiveServer.model;

import java.util.Base64;

public class User {
    private Integer userId;
    private  String userName;
    private  String password;
    private  String nickName;
    private Base64 avatar;
    private String gmail;
    private String forgotToken;
    private  Integer subcriberTotal;
    private StreamType favouraiteType;

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

    public StreamType getFavouraiteType() {
        return favouraiteType;
    }

    public void setFavouraiteType(StreamType favouraiteType) {
        this.favouraiteType = favouraiteType;
    }
}
