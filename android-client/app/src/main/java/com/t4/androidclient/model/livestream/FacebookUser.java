package com.t4.androidclient.model.livestream;

public class FacebookUser {
    private String userId;
    private String accessToken;

    public FacebookUser() {

    }

    public FacebookUser(String userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public String toString() {
        return "FB User id: " + this.userId + ", accessToken: " + this.accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
