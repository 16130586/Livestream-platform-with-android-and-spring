package com.t4.LiveServer.business.interfaze;

import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.entryParam.base.CommentEntryParams;
import org.springframework.web.bind.annotation.RequestBody;

public interface CommentBusiness {
    // commentToTopic(int owner, int streamId, string message, int source)
    ApiResponse commentToTopic(CommentEntryParams entryParams);
    // replyInParentComment(int owner, int streamId,int parentCommentId ,string message, int source)
    // edit(int owner , int streamId, string newMessage , int source)
    // delete(int owner , int streamId , int source)
}
