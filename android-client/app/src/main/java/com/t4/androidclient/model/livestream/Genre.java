package com.t4.androidclient.model.livestream;

public class Genre {
    private int id;
    private String genreString;

    public Genre() {

    }

    public Genre(int id, String genreString) {
        this.id = id;
        this.genreString = genreString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenreString() {
        return genreString;
    }

    public void setGenreString(String genreString) {
        this.genreString = genreString;
    }
}
