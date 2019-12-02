package com.t4.androidclient.model.livestream;

public class Group {
    private String name;
    private String id;
    private String privacy;

    public Group () {

    }

    public String toString() {
        return "name: " + this.name + ", id" + this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}
