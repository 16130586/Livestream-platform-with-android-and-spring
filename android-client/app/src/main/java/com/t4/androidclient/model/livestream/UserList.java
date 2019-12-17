package com.t4.androidclient.model.livestream;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class UserList {
    @JsonAlias({"data"})
    private List<User> userList;

    public UserList() {

    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
