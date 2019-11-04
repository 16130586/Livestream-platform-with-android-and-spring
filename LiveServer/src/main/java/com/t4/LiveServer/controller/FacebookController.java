package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.facebook.FacebookLiveBusiness;
import com.t4.LiveServer.core.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/facebook"})
public class FacebookController {
    @Autowired
    FacebookLiveBusiness facebookLiveBusiness;
    @GetMapping("/group/{access_token}")
    public ApiResponse getFacebookGroups(@PathVariable("access_token") String access_token) {
        ApiResponse response = new ApiResponse();
        if(null == access_token || "".equals(access_token)){
            response.statusCode = 400;
            response.message = "access_token must not be null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "all the information about user's group";
        response.data = facebookLiveBusiness.getFacebookGroups(access_token);
        return response;
    }

    @GetMapping("/pages/{access_token}")
    public ApiResponse getFacebookPages(@PathVariable ("access_token") String access_token) {
        ApiResponse response = new ApiResponse();
        if(null == access_token || "".equals(access_token)){
            response.statusCode = 400;
            response.message = "access_token must not be null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "all the information about user's pages";
        response.data = facebookLiveBusiness.getFacebookPages(access_token);
        return response;
    }
}
