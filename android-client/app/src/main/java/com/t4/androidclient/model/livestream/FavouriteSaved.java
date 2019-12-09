package com.t4.androidclient.model.livestream;

import java.util.Date;

public class FavouriteSaved {
    public LiveStream stream;
    public Date savedTime;

    public LiveStream getStream() {
        return stream;
    }

    public void setStream(LiveStream stream) {
        this.stream = stream;
    }

    public Date getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(Date savedTime) {
        this.savedTime = savedTime;
    }
}
