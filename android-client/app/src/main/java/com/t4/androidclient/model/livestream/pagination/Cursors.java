package com.t4.androidclient.model.livestream.pagination;

public class Cursors {
    String before;
    String after;

    public Cursors(){

    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

}
