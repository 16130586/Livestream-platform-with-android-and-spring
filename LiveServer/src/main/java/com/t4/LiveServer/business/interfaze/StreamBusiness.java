package com.t4.LiveServer.business.interfaze;


import com.t4.LiveServer.entryParam.base.Stream.CreatingStreamEntryParams;
import com.t4.LiveServer.model.Comment;
import com.t4.LiveServer.model.Stream;
import com.t4.LiveServer.model.StreamType;

import java.util.List;

public interface StreamBusiness {
    Object create(CreatingStreamEntryParams entryParams);
    Stream start(String id);
    Object stop(String id);
    List<Stream> getRecommendForCookieUser(int offset, int pageSize);
    List<Stream> getRecommendForUser(int userId, int offset, int pageSize);
    List<StreamType> getAllGenre();
    StreamType getGenreByName(String name);
    List<Stream> getStreamsByName(String streamName, int offset, int pageSize);
    List<Stream> getStreamsByNameAndType(String streamName, List<String> streamTypes, int offset, int pageSize);
    Comment saveComment(Comment comment);
	List<Stream> listStreamByTypeOfUser(int userID,int typeID);
	List<Stream>  getWatchedStreamsByUserID(int userID);
    List<Stream> getTrendingStreams(int offset, int pageSize);
    List<Comment> getCommentByVideoTime(int streamId , int videoTime);
    boolean upView(int streamId);
    Stream likeStream(int userId, int streamId);
    Stream dislikeStream(int userId, int streamId);
    int getLikeStatus(int userId, int streamId);
}
