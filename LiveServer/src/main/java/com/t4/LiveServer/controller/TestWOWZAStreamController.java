package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.facebook.FacebookLiveBusiness;
import com.t4.LiveServer.business.interfaze.Wowza.WOWZAStreamBusiness;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.model.wowza.StreamTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/test/wowza/streams"})
public class TestWOWZAStreamController {

    @Autowired
    WOWZAStreamBusiness WOWZAStreamBusiness;

    @Autowired
    FacebookLiveBusiness facebookLiveBusiness;

    public TestWOWZAStreamController() {
    }

    @PostMapping("/create")
    public ApiResponse createLiveStream(@RequestBody  String name) {
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.message="create live stream success!";
        response.data = WOWZAStreamBusiness.create(name);
        return response;
    }

    @GetMapping("/fetchAll")
    public ApiResponse fetchAllLiveStream() {
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.message="fetch all live stream!";
        response.data = WOWZAStreamBusiness.fetchAll();
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
        response.data = WOWZAStreamBusiness.fetchOne(id);
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
        response.data = WOWZAStreamBusiness.delete(id);
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
        response.data = WOWZAStreamBusiness.start(id);
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
        response.data = WOWZAStreamBusiness.stop(id);
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
        response.data = WOWZAStreamBusiness.reset(id);
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
        response.data = WOWZAStreamBusiness.regenerate(id);
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
        response.data = WOWZAStreamBusiness.fetchThumbnail(id);
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
        response.data = WOWZAStreamBusiness.fetchState(id);
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
        response.data = WOWZAStreamBusiness.fetchMetrics(id);
        return response;
    }

    @GetMapping("/versions")
    public ApiResponse getVersionsApi() {
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.message = "fetch versions live stream!";
        response.data = WOWZAStreamBusiness.fetchVersions();
        return response;

    }

    // testing purppose
    @PostMapping("/target")
    public ApiResponse createCustomStreamTarget(@RequestBody StreamTarget entry) {
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.message = "created custom live stream!";
        StreamTarget target = WOWZAStreamBusiness.createCustomStreamTarget(entry);
        if(target != null && target.createAt != null){
            response.data = target;
        }
        else {
            response.statusCode = 400;
            response.message = "Bad request!";
        }
        return response;
    }

    @GetMapping("/targets")
    public ApiResponse fetchAllCustomStreamTargets() {
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.message = "fetch all custom live stream!";
        response.data = WOWZAStreamBusiness.fetchAllCustomStreamTargets();
        return response;
    }

    @GetMapping("/target/{id}")
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
        response.data = WOWZAStreamBusiness.fetchCustomStreamTarget(id);
        return response;
    }

    @PatchMapping("/target/{id}")
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
        response.data = WOWZAStreamBusiness.updateCustomStreamTarget(id);
        return response;
    }

    @DeleteMapping("/target/{id}")
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
        response.data = WOWZAStreamBusiness.deleteCustomStreamTarget(id);
        return response;
    }

    @PutMapping("/target/regenerate/{id}")
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
        response.data = WOWZAStreamBusiness.regenerateCodeForAnyStreamTarget(id);
        return response;
    }

    @GetMapping("/transcoders/{id}")
    public ApiResponse getDefaultTransCoder(@PathVariable("id") String id){
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "the default " + id  + " trans coder information!";
        response.data = WOWZAStreamBusiness.getDefaultTransCoder(id);
        return response;
    }
    @GetMapping("/transcoders/{id}/outputs")
    public ApiResponse fetchAllOutputOfATransCoder(@PathVariable("id") String id){
        ApiResponse response = new ApiResponse();
        if(null == id || "".equals(id)){
            response.statusCode = 400;
            response.message = "ID stream must be not null!";
            response.data = null;
            response.errorCode = 1;
            return response;
        }
        response.statusCode = 200;
        response.message = "all output of trans coder  " + id  + " information!";
        response.data = WOWZAStreamBusiness.fetchAllOutputOfATransCoder(id);
        return response;
    }

    @GetMapping("/fb/group/{access_token}")
    public ApiResponse getFacebookGroups(@PathVariable ("access_token") String access_token) {
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

    @GetMapping("/fb/pages/{access_token}")
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
