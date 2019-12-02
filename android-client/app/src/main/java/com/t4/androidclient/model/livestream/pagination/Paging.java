package com.t4.androidclient.model.livestream.pagination;

public class Paging {
    Cursors cursors;
    private String previos;
    private String next;

    public Cursors getCursors() {
        return cursors;
    }

    public void setCursors(Cursors cursors) {
        this.cursors = cursors;
    }

    public String getPrevios() {
        return previos;
    }

    public void setPrevios(String previos) {
        this.previos = previos;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}