package com.t4.androidclient.model.helper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t4.androidclient.model.livestream.Comment;
import com.t4.androidclient.model.livestream.CommentResult;

import java.io.IOException;

public class CommentHelper {

    public static Comment parseComment(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            CommentResult commentResult = objectMapper.readValue(json, CommentResult.class);
            return commentResult.getComment();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
