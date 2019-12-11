package com.t4.LiveServer.business.interfaze;


import com.t4.LiveServer.entryParam.base.Stream.CreatingStreamEntryParams;
import com.t4.LiveServer.model.Comment;
import com.t4.LiveServer.model.Stream;
import com.t4.LiveServer.model.StreamType;

import java.util.Date;
import java.util.List;

public interface StreamBusiness {
    Object create(CreatingStreamEntryParams entryParams);
    Object start(String id);
    Object stop(String id);
    List<Stream> getRecommendForCookieUser(int offset, int pageSize);
    List<Stream> getRecommendForUser(int userId, int offset, int pageSize);
    List<StreamType> getAllGenre();
    StreamType getGenreByName(String name);
    List<Stream> getStreamsByName(String streamName, int offset, int pageSize);
    List<Stream> getStreamsByNameAndType(String streamName, List<String> streamTypes, int offset, int pageSize);
    Comment saveComment(Comment comment);
}
