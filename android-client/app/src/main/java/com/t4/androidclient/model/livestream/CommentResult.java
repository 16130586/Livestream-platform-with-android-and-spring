package com.t4.androidclient.model.livestream;

import com.fasterxml.jackson.annotation.JsonAlias;

public class CommentResult {

    @JsonAlias({"data"})
    private Comment comment;

    public CommentResult() {

    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
