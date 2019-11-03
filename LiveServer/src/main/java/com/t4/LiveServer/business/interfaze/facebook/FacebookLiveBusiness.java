package com.t4.LiveServer.business.interfaze.facebook;

import com.t4.LiveServer.config.FacebookConfig;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.model.facebook.group.Group;
import com.t4.LiveServer.model.facebook.page.Page;

import java.util.List;

public interface FacebookLiveBusiness {
    ApiResponse individualCreate(FacebookConfig fbConfig);
    ApiResponse groupCreate(FacebookConfig fbConfig , String groupId);
    ApiResponse pageCreate(FacebookConfig fbConfig , String pageId);
    ApiResponse stop(FacebookConfig fbConfig , String id);
    // lay page
    // lay page
    List<Group> getFacebookGroups(String accessToken);
    List<Page> getFacebookPages(String accessToken);
}
