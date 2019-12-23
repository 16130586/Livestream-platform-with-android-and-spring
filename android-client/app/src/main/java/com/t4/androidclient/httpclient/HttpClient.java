package com.t4.androidclient.httpclient;

import com.t4.androidclient.core.JsonHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class HttpClient {
    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private static Headers createAuthenticationHeader(String token) {
        Map<String, String> values = new HashMap<>();
        values.put("Authorization", "Bearer " + token);
        return Headers.of(values);
    }

    public static Request buildGetRequest(String url, String token) {
        Headers headers = createAuthenticationHeader(token);
        return new Request.Builder().headers(headers).url(url).get().build();
    }

    public static Request buildGetRequest(String url) {
        return new Request.Builder().url(url).get().build();
    }

    public static Request buildPostRequest(String url, Object values, String token) {
        Headers headers = createAuthenticationHeader(token);
        String jsonStringData = JsonHelper.serialize(values);
        Request request = new Request.Builder().headers(headers).url(url).post(RequestBody.create(jsonStringData, MEDIA_TYPE_JSON)).build();
        return request;
    }

    public static Request buildPostRequest(String url, Map<String, String> values) {
        String jsonStringData = JsonHelper.serialize(values);
        System.out.println(jsonStringData);
        Request request = new Request.Builder().url(url).post(RequestBody.create(jsonStringData, MEDIA_TYPE_JSON)).build();
        return request;
    }

    public static String execute(Request request) {
        try (Response response = httpClient.newCall(request).execute()) {
            String rs = response.body().string();
            return rs;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
