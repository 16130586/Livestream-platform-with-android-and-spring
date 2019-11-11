package com.t4.LiveServer.business.interfaze.facebook;

import com.t4.LiveServer.config.FacebookConfig;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.model.ForwardStream;
import com.t4.LiveServer.model.facebook.LiveStream;
import com.t4.LiveServer.model.facebook.page.Page;

import java.util.List;

public interface FacebookLiveBusiness {
    ForwardStream individualCreate(FacebookConfig fbConfig);
    ForwardStream groupCreate(FacebookConfig fbConfig , String groupId);
    ForwardStream pageCreate(FacebookConfig fbConfig , String pageId);
    ApiResponse stop(FacebookConfig fbConfig , String id);
    List<com.t4.LiveServer.model.facebook.group.Group> getFacebookGroups(String accessToken);
    List<Page> getFacebookPages(String accessToken);
}
