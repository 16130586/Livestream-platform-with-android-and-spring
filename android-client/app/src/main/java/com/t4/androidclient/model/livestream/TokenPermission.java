package com.t4.androidclient.model.livestream;

public class TokenPermission {

    private String permission;
    private String status;

    public  TokenPermission() {

    }

    public TokenPermission(String permission, String status) {
        this.permission = permission;
        this.status = status;
    }

    public String toString() {
        return "(" + this.permission + ": " + this.status + ")";
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
