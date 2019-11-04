package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.StreamBusiness;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.entryParam.base.Stream.CreatingStreamEntryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping({"/streams"})
public class StreamController {

    @Autowired
    StreamBusiness streamBusiness;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody CreatingStreamEntryParams entryParams){
        ApiResponse apiResponse = new ApiResponse();
        try{
            Object rs = streamBusiness.create(entryParams);
            apiResponse.statusCode = 200;
            apiResponse.message = "Ok";
            apiResponse.data = rs;
        }catch (Exception e){
            apiResponse.statusCode = 500;
            apiResponse.message = e.getMessage();
        }
        return apiResponse;
    }
    @PostMapping("/{id}/start")
    public ApiResponse start(@PathVariable("id") String id){
        ApiResponse apiResponse = new ApiResponse();
        try{
            Object rs = streamBusiness.start(id);
            apiResponse.statusCode = 200;
            apiResponse.message = "Ok";
            apiResponse.data = rs;
        }catch (Exception e){
            apiResponse.statusCode = 500;
            apiResponse.message = e.getMessage();
        }
        return apiResponse;
    }
    @PostMapping("/{id}/stop")
    public ApiResponse stop(@PathVariable("id") String id){
        ApiResponse apiResponse = new ApiResponse();
        try{
            Object rs = streamBusiness.stop(id);
            apiResponse.statusCode = 200;
            apiResponse.message = "Ok";
            apiResponse.data = rs;
        }catch (Exception e){
            apiResponse.statusCode = 500;
            apiResponse.message = e.getMessage();
        }
        return apiResponse;
    }
}
