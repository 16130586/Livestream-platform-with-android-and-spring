package com.t4.androidclient.model.livestream;

import java.util.List;

public class LiveStream {
    private String title;
    private String thumbnail;
    private List<String> genreList;
    private FacebookUser facebookUser;

    public LiveStream() {

    }


    public String toString() {
        if (facebookUser == null) {
            return this.title + ", "
                    + this.thumbnail +", "
                    + this.genreList.toString();
        }
        return this.title + ", "
                + this.thumbnail +", "
                + this.genreList.toString() + ", "
                + this.facebookUser.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<String> genreList) {
        this.genreList = genreList;
    }

    public FacebookUser getFacebookUser() {
        return facebookUser;
    }

    public void setFacebookUser(FacebookUser facebookUser) {
        this.facebookUser = facebookUser;
    }
}
