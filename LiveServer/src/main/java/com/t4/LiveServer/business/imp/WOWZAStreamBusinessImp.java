package com.t4.LiveServer.business.imp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.t4.LiveServer.business.interfaze.WOWZAStreamBusiness;
import com.t4.LiveServer.core.JsonHelper;
import com.t4.LiveServer.middleware.RestTemplateHandleException;
import com.t4.LiveServer.model.wowza.*;
import org.apache.el.stream.Stream;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class WOWZAStreamBusinessImp implements com.t4.LiveServer.business.interfaze.WOWZAStreamBusiness {
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
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM + "/" + id, HttpMethod.GET, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
        return result.getBody();
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String delete(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM + "/" + id, HttpMethod.DELETE, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
        return result.getBody();
    }

    @Override
    public String start(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM + "/" + id + "/start", HttpMethod.PUT, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @Override
    public String stop(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM + "/" + id + "/stop", HttpMethod.PUT, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
        return result.getBody();
    }

    @Override
    public String reset(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM + "/" + id + "/reset", HttpMethod.PUT, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
        return result.getBody();
    }

    @Override
    public String regenerate(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM + "/" + id + "/regenerate_connection_code", HttpMethod.PUT, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
        return result.getBody();
    }

    @Override
    public String fetchThumbnail(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM + "/" + id + "/thumbnail_url", HttpMethod.GET, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
        return result.getBody();
    }

    @Override
    public String fetchState(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM + "/" + id + "/state", HttpMethod.GET, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
        return result.getBody();
    }

    @Override
    public String fetchMetrics(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM + "/" + id + "/stats", HttpMethod.GET, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
        return result.getBody();
    }

    @Override
    public String fetchVersions() {
        ResponseEntity<String> rs = restTemplate.exchange("https://api.cloud.wowza.com/api/versions", HttpMethod.GET, new HttpEntity<>(WowzaStream.getWowzaConfigHeaders()), String.class);
        return rs.getBody();
    }

    @Override
    public StreamTarget createCustomStreamTarget(final StreamTarget streamTarget) {
        StreamTarget customStreamtarget = streamTarget; // TBD
        String jsonData = JsonHelper.serialize(customStreamtarget);
        jsonData = "{\"stream_target_custom\":" + jsonData + "}";
        HttpEntity<String> entity = new HttpEntity<>(jsonData, WowzaStream.getWowzaConfigHeaders());
        ResponseEntity<String> rs = restTemplate.exchange(WowzaStream.URL_STREAM_TARGETS + "/custom", HttpMethod.POST, entity, String.class);
        customStreamtarget = JsonHelper.deserialize(DeserializationFeature.UNWRAP_ROOT_VALUE,rs.getBody() , StreamTarget.class);
        return customStreamtarget;
    }

    @Override
    public String fetchAllCustomStreamTargets() {
        ResponseEntity<String> rs = restTemplate.exchange(WowzaStream.URL_STREAM_TARGETS + "/custom", HttpMethod.GET, new HttpEntity<>(WowzaStream.getWowzaConfigHeaders()), String.class);
        return rs.getBody();
    }

    @Override
    public String fetchCustomStreamTarget(String id) {
        ResponseEntity<String> rs = restTemplate.exchange(WowzaStream.URL_STREAM_TARGETS + "/custom/" + id, HttpMethod.GET, new HttpEntity<>(WowzaStream.getWowzaConfigHeaders()), String.class);
        return rs.getBody();
    }

    @Override
    public String updateCustomStreamTarget(String id) {
        Object customStreamtarget = null; // TBD
        String jsonData = JsonHelper.serialize(customStreamtarget);
        jsonData = "{\"stream_target_custom\":" + jsonData + "}";
        HttpEntity<String> entity = new HttpEntity<>(jsonData, WowzaStream.getWowzaConfigHeaders());
        ResponseEntity<String> rs = restTemplate.exchange(WowzaStream.URL_STREAM_TARGETS + "/custom/" + id, HttpMethod.PATCH, entity, String.class);
        return "TBD";
    }

    @Override
    public String deleteCustomStreamTarget(String id) {
        ResponseEntity<String> rs = restTemplate.exchange(WowzaStream.URL_STREAM_TARGETS + "/custom/" + id, HttpMethod.DELETE, new HttpEntity<>(WowzaStream.getWowzaConfigHeaders()), String.class);
        return rs.getBody();
    }

    @Override
    public String regenerateCodeForAnyStreamTarget(String id) {
        ResponseEntity<String> rs = restTemplate.exchange(WowzaStream.URL_STREAM_TARGETS + id + "/regenerate_connection_code", HttpMethod.DELETE, new HttpEntity<>(WowzaStream.getWowzaConfigHeaders()), String.class);
        return rs.getBody();
    }

    @Override
    public String getDefaultTransCoder(String id) {
        ResponseEntity<String> rs = restTemplate.
                exchange(WowzaStream.URL_TRANSCODERS + "/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(WowzaStream.getWowzaConfigHeaders()), String.class);
        return rs.getBody();
    }

    @Override
    public List<StreamOutput> fetchAllOutputOfATransCoder(String id) {
        ResponseEntity<String> rs = restTemplate.
                exchange(WowzaStream.URL_TRANSCODERS + "/" + id + "/outputs",
                        HttpMethod.GET,
                        new HttpEntity<>(WowzaStream.getWowzaConfigHeaders()), String.class);
        System.out.println(rs.getBody());
        if (rs.getBody() != null) {
            ListOutputStreamTarget output = JsonHelper.deserialize(rs.getBody(), ListOutputStreamTarget.class);
            if (output != null)
                return output.outputs;
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

}
