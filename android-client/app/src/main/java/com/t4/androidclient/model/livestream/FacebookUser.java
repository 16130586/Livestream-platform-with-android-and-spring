package com.t4.androidclient.model.livestream;

public class FacebookUser {
    private String userId;
    private String accessToken;

    // facebook option 1: personal, 2: group, 3: page
    private String facebookOption;
    private String facebookOptionId;

    public FacebookUser() {

    }

    public FacebookUser(String userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public String toString() {
        String string = "FB User id: " + this.userId
                + ", accessToken: " + this.accessToken
                + ", option: " + this.facebookOption
                + ", option id: " + this.facebookOptionId;

        return string;
    }

    public String getFacebookOption() {
        return facebookOption;
    }

    public void setFacebookOption(String facebookOption) {
        this.facebookOption = facebookOption;
    }

    public String getFacebookOptionId() {
        return facebookOptionId;
    }

    public void setFacebookOptionId(String facebookOptionId) {
        this.facebookOptionId = facebookOptionId;
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
