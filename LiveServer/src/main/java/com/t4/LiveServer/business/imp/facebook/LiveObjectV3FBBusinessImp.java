package com.t4.LiveServer.business.imp.facebook;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t4.LiveServer.business.interfaze.facebook.FacebookLiveBusiness;
import com.t4.LiveServer.config.FacebookConfig;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.middleware.RestTemplateHandleException;
import com.t4.LiveServer.model.facebook.group.FacebookGroup;
import com.t4.LiveServer.model.facebook.group.Group;
import com.t4.LiveServer.model.facebook.page.FacebookPage;
import com.t4.LiveServer.model.facebook.page.Page;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

public class LiveObjectV3FBBusinessImp implements FacebookLiveBusiness {
    private RestTemplate restTemplate = new RestTemplateBuilder().errorHandler(new RestTemplateHandleException()).build();

    private ApiResponse create(FacebookConfig fbConfig , String streamToId) {
        ApiResponse response = new ApiResponse();
        try{
            String fullyURL = FacebookConfig.V3_BASE_URL
                    + "/" + streamToId + "/live_videos?status=LIVE_NOW&access_token="
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

    @Override
    public ApiResponse individualCreate(FacebookConfig fbConfig) {
        String relatedIdWithToken = "me";
        return this.create(fbConfig , relatedIdWithToken);
    }

    @Override
    public ApiResponse groupCreate(FacebookConfig fbConfig , String groupId) {
        return this.create(fbConfig , groupId);
    }

    @Override
    public ApiResponse pageCreate(FacebookConfig fbConfig , String pageId) {
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

    @Override
    public List<Group> getFacebookGroups (String accessToken) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String url = "https://graph.facebook.com/v5.0/me/groups?access_token=" + accessToken + "&limit=5000";
        ResponseEntity<String> rs = restTemplate.getForEntity(url, String.class);
        try {
            FacebookGroup facebookGroup = objectMapper.readValue(rs.getBody(), FacebookGroup.class);
            return facebookGroup.data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Page> getFacebookPages(String accessToken) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String url = "https://graph.facebook.com/v5.0/me/accounts?access_token=" + accessToken;
        ResponseEntity<String> rs = restTemplate.getForEntity(url, String.class);
        try {
            FacebookPage facebookPage = objectMapper.readValue(rs.getBody(), FacebookPage.class);
            return facebookPage.data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
