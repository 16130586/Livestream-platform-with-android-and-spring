package com.t4.LiveServer.business.imp;

import com.t4.LiveServer.business.interfaze.FacebookLiveBusiness;
import com.t4.LiveServer.config.FacebookConfig;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.core.JsonHelper;
import com.t4.LiveServer.middleware.RestTemplateHandleException;
import com.t4.LiveServer.model.facebook.LiveStream;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class LiveObjectV3FBBusinessImp implements FacebookLiveBusiness {
    private RestTemplate restTemplate = new RestTemplateBuilder().errorHandler(new RestTemplateHandleException()).build();

    private LiveStream create(FacebookConfig fbConfig , String streamToId) {
        LiveStream created = null;
        try{
            String fullyURL = FacebookConfig.V3_BASE_URL
                    + "/" + streamToId + "/live_videos?status=LIVE_NOW&access_token="
                    + fbConfig.accessToken;
            ResponseEntity<String> result = restTemplate.exchange(
                    fullyURL, HttpMethod.POST, null ,String.class);
            created = JsonHelper.deserialize(result.getBody() , LiveStream.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return created;
    }

    @Override
    public LiveStream individualCreate(FacebookConfig fbConfig) {
        String relatedIdWithToken = "me";
        return this.create(fbConfig , relatedIdWithToken);
    }

    @Override
    public LiveStream groupCreate(FacebookConfig fbConfig , String groupId) {
        return this.create(fbConfig , groupId);
    }

    @Override
    public LiveStream pageCreate(FacebookConfig fbConfig , String pageId) {
        return this.create(fbConfig , pageId);
    }


    @Override
    public ApiResponse stop(FacebookConfig fbConfig , String id) {
        ApiResponse response = new ApiResponse();
        try{
            String fullyURL = FacebookConfig.V3_BASE_URL + "/" + id
                    + "?end_live_video=true&access_token="
                    + fbConfig.accessToken;
            ResponseEntity<String> result = restTemplate.exchange(fullyURL, HttpMethod.POST, null ,String.class);
            response.statusCode = 200;
            response.message = "OK";
            response.data = result.getBody();
        }catch (Exception e){
            response.statusCode = 500;
            response.message = e.getMessage();
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }
}
