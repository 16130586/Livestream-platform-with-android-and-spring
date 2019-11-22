package com.t4.androidclient.model.livestream;

import java.util.List;

public class LiveStream {
    private String title;
    private String thumbnailUrl;
    private List<String> genreList;
    private FacebookUser facebookUser;

    public LiveStream() {

    }


    public String toString() {
        return this.title + ", "
                + this.thumbnailUrl +", "
                + this.genreList.toString() + ", "
                + this.facebookUser.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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
