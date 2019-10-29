package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.StreamBusiness;
import com.t4.LiveServer.core.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/stream"})
public class StreamController {

    @Autowired
    StreamBusiness streamBusiness;

    public StreamController() {
    }

    @PostMapping("/create")
    public ApiResponse createLiveStream() {
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.message="create live stream success!";
        response.data = streamBusiness.create();
        return response;
    }

    @GetMapping("/fetchAll")
    public ApiResponse fetchAllLiveStream() {
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.message="fetch all live stream!";
        response.data = streamBusiness.fetchAll();
        return response;
    }

    @GetMapping("/fetch/{id}")
    public ApiResponse fetchALiveStream(@PathVariable("id") final String id) {
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "fetch one live stream!";
        response.data = streamBusiness.fetchOne(id);
        return response;
    }

    @PatchMapping("/update")
    public ApiResponse updateALiveStream() {
        ApiResponse response = new ApiResponse();
        response.statusCode = 400;
        response.message = "TBD!";
        response.data = null;
        response.errorCode = 1;
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteALiveStream(@PathVariable("id") final String id) {
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "delete one live stream!";
        response.data = streamBusiness.delete(id);
        return response;
    }

    @PutMapping("/start/{id}")
    public ApiResponse startALiveStream(@PathVariable("id") final String id) {
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "start one live stream!";
        response.data = streamBusiness.start(id);
        return response;
    }

    @PutMapping("/stop/{id}")
    public ApiResponse stopALiveStream(@PathVariable("id") final String id) {
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "stop one live stream!";
        response.data = streamBusiness.stop(id);
        return response;
    }

    @PutMapping("/reset/{id}")
    public ApiResponse resetALiveStream(@PathVariable("id") final String id) {
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "reset one live stream!";
        response.data = streamBusiness.reset(id);
        return response;
    }

    @PutMapping("/regenerateCode/{id}")
    public ApiResponse regenerateConnectionCode(@PathVariable("id") final String id) {
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "regenerate connection code!";
        response.data = streamBusiness.regenerate(id);
        return response;
    }

    @GetMapping("/fetchThumbnail/{id}")
    public ApiResponse fetchThumbnailURL(@PathVariable("id") final String id) {
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "fetch thumbnail!";
        response.data = streamBusiness.fetchThumbnail(id);
        return response;
    }

    @GetMapping("/fetchState/{id}")
    public ApiResponse fetchState(@PathVariable("id") final String id) {
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "fetch state live stream!";
        response.data = streamBusiness.fetchState(id);
        return response;
    }

    @GetMapping("/fetchMetrics/{id}")
    public ApiResponse fetchMetrics(@PathVariable("id") final String id) {
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "fetch metrics live stream!";
        response.data = streamBusiness.fetchMetrics(id);
        return response;
    }

    @GetMapping("/versions")
    public ApiResponse getVersionsApi() {
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.message = "fetch versions live stream!";
        response.data = streamBusiness.fetchVersions();
        return response;

    }

    @PostMapping("/custom")
    public ApiResponse createCustomStreamTarget() {
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.message = "create custom live stream!";
        response.data = streamBusiness.createCustomStreamTarget();
        return response;
    }

    @GetMapping("/custom")
    public ApiResponse fetchAllCustomStreamTargets() {
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.message = "fetch all custom live stream!";
        response.data = streamBusiness.fetchAllCustomStreamTargets();
        return response;
    }

    @GetMapping("/custom/{id}")
    public ApiResponse fetchCustomStreamTarget(@PathVariable("id") String id) {
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "fetch a custom live stream!";
        response.data = streamBusiness.fetchCustomStreamTarget(id);
        return response;
    }

    @PatchMapping("/custom/{id}")
    public ApiResponse updateCustomStreamTarget(@PathVariable("id") String id) {
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "update a custom live stream!";
        response.data = streamBusiness.updateCustomStreamTarget(id);
        return response;
    }

    @DeleteMapping("/custom/{id}")
    public ApiResponse deleteCustomStreamTarget(@PathVariable("id") String id) {
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "delete a custom live stream!";
        response.data = streamBusiness.deleteCustomStreamTarget(id);
        return response;
    }

    @PutMapping("/custom/regenerate/{id}")
    public ApiResponse regenerateCodeForAnyStreamTarget(@PathVariable("id") String id) {
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "regenerate code for any custom live stream!";
        response.data = streamBusiness.deleteCustomStreamTarget(id);
        return response;
    }
}
