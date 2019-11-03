package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.facebook.FacebookLiveBusiness;
import com.t4.LiveServer.business.interfaze.SampleBusiness;
import com.t4.LiveServer.config.FacebookConfig;
import com.t4.LiveServer.core.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/sample"})
public class SampleController {

    //always clearly declare method
    @Autowired
    SampleBusiness sampleBusiness;
    @RequestMapping(value = {"/sample"} , method = RequestMethod.GET)
    public ApiResponse verb(){
        return sampleBusiness.sampleEcho();
    }
    @RequestMapping(value = {"/sample2"} , method = RequestMethod.GET)
    public ApiResponse verb2(@RequestParam(name = "id") final String id){
        // if you need to validate params
        // this layer only validate for not null, not use for validate constraints
        // or business constraints
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "Just a example message!";
            response.data = null;
            // team defined error code for mutil purpose (localization, mutil handling for mutil client)
            response.errorCode = 1;
            return response;
        }
        return sampleBusiness.sampleEcho();
    }
    @RequestMapping(value = {"/sample3"} , method = RequestMethod.GET)
    public ApiResponse verb3(@RequestParam(name = "id") final String id){
        // if you need to validate params
        // this layer only validate for not null, not use for validate constraints
        // or business constraints
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "Just a example message!";
            response.data = null;
            // team defined error code for mutil purpose (localization, mutil handling for mutil client)
            response.errorCode = 1;
            return response;
        }
        return sampleBusiness.sampleEcho(id);
    }

    @Autowired
    FacebookLiveBusiness facebookLiveBusiness;
    FacebookConfig fbConfig = new FacebookConfig();
    @GetMapping("/create")
    public ApiResponse create(){
        return facebookLiveBusiness.individualCreate(fbConfig);
    }

    @GetMapping("/stop/{id}")
    public ApiResponse create(@PathVariable("id") String id){
        return facebookLiveBusiness.stop(fbConfig , id);
    }
}