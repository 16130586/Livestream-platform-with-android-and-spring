package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.CommentBusiness;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.entryParam.base.CommentEntryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public class CommentController {
    // replyInParentComment(int owner, int streamId,int parentCommentId ,string message, int source)
    // edit(int owner , int streamId, string newMessage , int source)
    // delete(int owner , int streamId , int source)

    @Autowired
    CommentBusiness commentBusiness;

    @PostMapping("/commentToTopic")
    public ApiResponse commentToTopic(@RequestBody CommentEntryParams entryParams) {
        ApiResponse response = new ApiResponse();
        if (null == entryParams) {
            response.statusCode = 400;
            response.message = "Just a example message!";
            response.dataAsString = null;
            // team defined error code for mutil purpose (localization, mutil handling for mutil client)
            response.errorCode = 1;
            return response;
        }
        // valid
        return commentBusiness.commentToTopic(entryParams);
    }
}
