package com.t4.LiveServer.business.interfaze;


import com.t4.LiveServer.entryParam.base.Stream.CreatingStreamEntryParams;
import com.t4.LiveServer.model.Comment;
import com.t4.LiveServer.model.StreamType;

import java.util.List;

public interface StreamBusiness {
    Object create(CreatingStreamEntryParams entryParams);
    Object start(String id);
    Object stop(String id);
    Object getRecommendForUser(List<StreamType> streamTypes, int offset, int pageSize);
    Object getAllGenre();
    Object getGenreByName(String name);
    Object getStreamsByName(String streamName);
    Object getStreamsByNameAndType(String streamName, List<StreamType> streamTypes);
    Comment saveComment(Comment comment);
}
