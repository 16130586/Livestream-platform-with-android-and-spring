package com.t4.LiveServer.business.interfaze;

import com.t4.LiveServer.config.FacebookConfig;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.model.facebook.LiveStream;

public interface FacebookLiveBusiness {
    LiveStream individualCreate(FacebookConfig fbConfig);
    LiveStream groupCreate(FacebookConfig fbConfig , String groupId);
    LiveStream pageCreate(FacebookConfig fbConfig , String pageId);
    ApiResponse stop(FacebookConfig fbConfig , String id);
}
