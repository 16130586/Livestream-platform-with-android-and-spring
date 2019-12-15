package com.t4.androidclient.model.inbox;

public class Inbox {
    private String avatar;
    private String message;
    private String name;

    public Inbox(String avatar, String message, String name) {
        this.avatar = avatar;
        this.message = message;
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setOwner(String owner) {
        this.name = owner;
    }
}
