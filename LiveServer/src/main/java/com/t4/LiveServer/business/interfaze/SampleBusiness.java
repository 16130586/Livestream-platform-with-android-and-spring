package com.t4.LiveServer.business.interfaze;

import com.t4.LiveServer.core.ApiResponse;

/*
*
* With a business, we have 2 types:
*  + a business using Restemplate to call another api
*  + a business using addition data access layer to call to database
*
* We just define all required methods for our business
*/
public interface SampleBusiness {
    ApiResponse sampleEcho();
    ApiResponse sampleEcho(String id);
}
