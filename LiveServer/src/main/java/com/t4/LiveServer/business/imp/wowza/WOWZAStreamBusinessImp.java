package com.t4.LiveServer.business.imp.wowza;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.t4.LiveServer.business.interfaze.wowza.WOWZAStreamBusiness;
import com.t4.LiveServer.core.JsonHelper;
import com.t4.LiveServer.entryParam.base.Stream.CreatingStreamEntryParams;
import com.t4.LiveServer.entryParam.base.Wowza.AdditionOutputStreamTargetToTransCoderEntryParam;
import com.t4.LiveServer.middleware.RestTemplateHandleException;
import com.t4.LiveServer.model.wowza.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class WOWZAStreamBusinessImp implements WOWZAStreamBusiness {
    private RestTemplate restTemplate = new RestTemplateBuilder().errorHandler(new RestTemplateHandleException()).build();


    @Override
    public WowzaStream create(CreatingStreamEntryParams entryParams) {
        WowzaStream wowzaStream = new WowzaStream(1280, 720, "pay_as_you_go", "asia_pacific_singapore",
                "other_rtmp", entryParams.name, "transcoded");

        wowzaStream.isRecording = entryParams.isStored > 0;
        wowzaStream.hostedPageTitle = entryParams.name;
        if(entryParams.thumbnail != null && entryParams.thumbnail.length() > 0)
            wowzaStream.posterImage = entryParams.thumbnail;
        String jsonData = JsonHelper.serialize(wowzaStream);
        jsonData = "{\"live_stream\":" + jsonData + "}";
        HttpEntity<String> requestBody = new HttpEntity<>(jsonData, wowzaStream.getWowzaConfigHeaders());
        ResponseEntity<String> result = restTemplate.exchange(wowzaStream.URL_LIVE_STREAM, HttpMethod.POST, requestBody, String.class);
        return JsonHelper.deserialize(DeserializationFeature.UNWRAP_ROOT_VALUE, result.getBody(), WowzaStream.class);
    }

    @Override
    public List<WowzaStream> fetchAll() {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM, HttpMethod.GET, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
        ListWowzaStream streams = JsonHelper.deserialize(
                result.getBody(), ListWowzaStream.class);
        if (streams != null && streams.streams != null && streams.streams.size() > 0) {
            return streams.streams;
        }
        return Collections.emptyList();
    }

    @Override
    public WowzaStream fetchOne(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM + "/" + id, HttpMethod.GET, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
        return JsonHelper.deserialize(DeserializationFeature.UNWRAP_ROOT_VALUE, result.getBody(), WowzaStream.class);
    }

    @Override
    public String update(WowzaStream entry) {
        String data = "{\"live_stream\": " + JsonHelper.serialize(entry) + "}";
        HttpEntity<String> requestBody = new HttpEntity<>(
                data, WowzaStream.getWowzaConfigHeaders());
        ResponseEntity<String> result = restTemplate.exchange(
                WowzaStream.URL_LIVE_STREAM + "/" + entry.id,
                HttpMethod.PATCH, requestBody, String.class);
        return result.getBody();
    }

    @Override
    public String delete(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM + "/" + id, HttpMethod.DELETE, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
        return result.getBody();
    }

    @Override
    public WowzaStream start(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM + "/" + id + "/start", HttpMethod.PUT, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
        return JsonHelper.deserialize(DeserializationFeature.UNWRAP_ROOT_VALUE, result.getBody(), WowzaStream.class);
    }

    @Override
    public WowzaStream stop(String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM + "/" + id + "/stop", HttpMethod.PUT, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
        return JsonHelper.deserialize(DeserializationFeature.UNWRAP_ROOT_VALUE, result.getBody(), WowzaStream.class);
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
        customStreamtarget = JsonHelper.deserialize(DeserializationFeature.UNWRAP_ROOT_VALUE, rs.getBody(), StreamTarget.class);
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
    public TransCoder getDefaultTransCoder(String id) {
        ResponseEntity<String> rs = restTemplate.
                exchange(WowzaStream.URL_TRANSCODERS + "/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(WowzaStream.getWowzaConfigHeaders()), String.class);
        TransCoder transCoders = JsonHelper.deserialize(DeserializationFeature.UNWRAP_ROOT_VALUE
                , rs.getBody(), TransCoder.class);
        return transCoders;
    }

    @Override
    public String addOutputStreamTargetToTransCoderOfAStream(AdditionOutputStreamTargetToTransCoderEntryParam entry) {
        Map<String, String> wowzaTargetStreamObject = new HashMap<>();
        wowzaTargetStreamObject.put("stream_target_id", entry.streamTargetId);
        wowzaTargetStreamObject.put("use_stream_target_backup_url", "false");
        String jsonData = "{\"output_stream_target\" : " + JsonHelper.serialize(wowzaTargetStreamObject) + "}";

        HttpEntity<String> entity = new HttpEntity<>(jsonData, WowzaStream.getWowzaConfigHeaders());
        String fullyUrl = WowzaStream.URL_TRANSCODERS + "/" + entry.transCoderId +
                "/outputs/" + entry.outputId +
                "/output_stream_targets";
        ResponseEntity<String> rs = restTemplate.
                exchange(fullyUrl,
                        HttpMethod.POST,
                        entity, String.class);
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

    @Override
    public StreamOutput getStreamOutput(String streamId, String name) {
        List<StreamOutput> outputs = fetchAllOutputOfATransCoder(streamId);
        Optional<StreamOutput> rs = outputs.stream().filter(transCoder -> {
            return transCoder.name.contains(name);
        }).findAny();
        return rs.isPresent() ? rs.get() : null;
    }
}
