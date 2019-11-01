package com.t4.LiveServer.business.interfaze;

import com.t4.LiveServer.config.FacebookConfig;
import com.t4.LiveServer.core.ApiResponse;

public interface FacebookLiveBusiness {
    ApiResponse individualCreate(FacebookConfig fbConfig);
    ApiResponse groupCreate(FacebookConfig fbConfig , String groupId);
    ApiResponse pageCreate(FacebookConfig fbConfig , String pageId);
    ApiResponse stop(FacebookConfig fbConfig , String id);
}
