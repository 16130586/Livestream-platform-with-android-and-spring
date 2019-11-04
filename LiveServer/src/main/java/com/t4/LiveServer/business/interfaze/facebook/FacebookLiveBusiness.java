package com.t4.LiveServer.business.interfaze.facebook;

import com.t4.LiveServer.config.FacebookConfig;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.model.facebook.LiveStream;
import com.t4.LiveServer.model.facebook.page.Page;

import java.util.List;

public interface FacebookLiveBusiness {
    LiveStream individualCreate(FacebookConfig fbConfig);
    LiveStream groupCreate(FacebookConfig fbConfig , String groupId);
    LiveStream pageCreate(FacebookConfig fbConfig , String pageId);
    ApiResponse stop(FacebookConfig fbConfig , String id);
    List<com.t4.LiveServer.model.facebook.group.Group> getFacebookGroups(String accessToken);
    List<Page> getFacebookPages(String accessToken);
}
