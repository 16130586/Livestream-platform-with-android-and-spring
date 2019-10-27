package com.t4.LiveServer.business.imp;

import com.t4.LiveServer.business.interfaze.StreamBusiness;
import com.t4.LiveServer.core.JsonHelper;
import com.t4.LiveServer.middleware.RestTemplateHandleException;
import com.t4.LiveServer.model.wowza.WowzaStream;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StreamBusinessImp implements StreamBusiness {
    private RestTemplate restTemplate = new RestTemplateBuilder().errorHandler(new RestTemplateHandleException()).build();


    @Override
    public String create() {
        WowzaStream wowzaStream = new WowzaStream(1280, 720, "pay_as_you_go", "asia_pacific_singapore", "other_rtmp", "test", "transcoded");
        String jsonData = JsonHelper.serialize(wowzaStream);
        jsonData = "{\"live_stream\":" + jsonData + "}";
        HttpEntity<String> requestBody = new HttpEntity<>(jsonData, wowzaStream.getWowzaConfigHeaders());

        ResponseEntity<String> result = restTemplate.exchange(wowzaStream.URL_LIVE_STREAM, HttpMethod.POST, requestBody, String.class);
        return result.getBody();
    }

    @Override
    public String fetchAll() {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM, HttpMethod.GET, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @Override
    public String fetchOne(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id, HttpMethod.GET, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String delete(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id, HttpMethod.DELETE,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @Override
    public String start(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id+"/start", HttpMethod.PUT,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @Override
    public String stop(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id+"/stop", HttpMethod.PUT,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @Override
    public String reset(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id+"/reset", HttpMethod.PUT,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @Override
    public String regenerate(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id+"/regenerate_connection_code", HttpMethod.PUT,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @Override
    public String fetchThumbnail(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id+"/thumbnail_url", HttpMethod.GET,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @Override
    public String fetchState(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id+"/state", HttpMethod.GET,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @Override
    public String fetchMetrics(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id+"/stats", HttpMethod.GET,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @Override
    public String fetchVersions() {
        ResponseEntity<String> rs = restTemplate.exchange("https://api.cloud.wowza.com/api/versions", HttpMethod.GET, new HttpEntity<>(WowzaStream.getWowzaConfigHeaders()), String.class);

        return rs.getBody().toString();
    }
}
