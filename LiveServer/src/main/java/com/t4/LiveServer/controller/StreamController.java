package com.t4.LiveServer.controller;

import com.t4.LiveServer.core.JsonHelper;
import com.t4.LiveServer.model.wowza.WowzaStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping({"/stream"})
public class StreamController {
    private RestTemplate restTemplate = new RestTemplateBuilder().errorHandler(new RestTemplateHandleException()).build();

    public StreamController() {
    }

    @RequestMapping({"/hello"})
    public abc getHello() {
        return new abc();
    }

    @PostMapping("/create")
    public String createLiveStream() {
        WowzaStream wowzaStream = new WowzaStream(1280, 720, "pay_as_you_go", "asia_pacific_singapore", "other_rtmp", "test", "transcoded");
        String jsonData = JsonHelper.serialize(wowzaStream);
        jsonData = "{\"live_stream\":" + jsonData + "}";
        HttpEntity<String> requestBody = new HttpEntity<>(jsonData, wowzaStream.getWowzaConfigHeaders());

        ResponseEntity<String> result = restTemplate.exchange(wowzaStream.URL_LIVE_STREAM, HttpMethod.POST, requestBody, String.class);
        return result.getBody();
    }

    @GetMapping("/fetchAll")
    public String fetchAllLiveStream() {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM, HttpMethod.GET, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @GetMapping("/fetch/{id}")
    public String fetchALiveStream(@PathVariable("id") String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id, HttpMethod.GET, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @PatchMapping("/update")
    public String updateALiveStream() {
        return "TBD";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteALiveStream(@PathVariable("id") String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id, HttpMethod.DELETE,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @PutMapping("/start/{id}")
    public String startALiveStream(@PathVariable("id") String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id+"/start", HttpMethod.PUT,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @PutMapping("/stop/{id}")
    public String stopALiveStream(@PathVariable("id") String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id+"/stop", HttpMethod.PUT,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @PutMapping("/reset/{id}")
    public String resetALiveStream(@PathVariable("id") String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id+"/reset", HttpMethod.PUT,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @PutMapping("/regenerateCode/{id}")
    public String regenerateConnectionCode(@PathVariable("id") String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id+"/regenerate_connection_code", HttpMethod.PUT,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @GetMapping("/fetchThumbnail/{id}")
    public String fetchThumbnailURL(@PathVariable("id") String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id+"/thumbnail_url", HttpMethod.GET,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @GetMapping("/fetchState/{id}")
    public String fetchState(@PathVariable("id") String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id+"/state", HttpMethod.GET,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @GetMapping("/fetchMetrics/{id}")
    public String fetchMetrics(@PathVariable("id") String id) {
        ResponseEntity<String> result = restTemplate.exchange(WowzaStream.URL_LIVE_STREAM+"/"+id+"/stats", HttpMethod.GET,new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);

        return result.getBody();
    }

    @GetMapping("/versions")
    public String getVersionsApi() {
        WowzaStream wowzaStream = new WowzaStream(1280, 720, "pay_as_you_go", "asia_pacific_singapore", "other_rtmp", "CREATE_" + System.currentTimeMillis(), "transcoded");

        ResponseEntity<String> rs = restTemplate.exchange("https://api.cloud.wowza.com/api/versions", HttpMethod.GET, new HttpEntity<>(WowzaStream.getWowzaConfigHeaders()), String.class);
        return rs.getBody().toString();

    }
}
