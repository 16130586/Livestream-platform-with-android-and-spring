package com.t4.LiveServer.business.imp;


import com.t4.LiveServer.business.interfaze.SampleBusiness;
import com.t4.LiveServer.core.ApiResponse;
import org.springframework.stereotype.Service;

/*
* Ensuring annotated it with @Service
*/
@Service
public class SampleBusinessImp implements SampleBusiness {

    public SampleBusinessImp(){

    }
    @Override
    public ApiResponse sampleEcho() {
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.dataAsString = "SampleBusinessImp";
        response.message = "Oke";
        return response;
    }

    @Override
    public ApiResponse sampleEcho(String id) {
        // business validation
        ApiResponse response = new ApiResponse();
        if(id.length() < 10) {
            response.errorCode = 1;
            response.statusCode = 400;
            response.dataAsString = null;
            response.message = "length less than 10";
            return response;
        }

        response.statusCode = 200;
        response.dataAsString = "oke you got it! "  + id;
        response.message = "oke";
        return response;
    }
}
