package com.t4.androidclient.httpclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient {
    private static final OkHttpClient httpClient = new OkHttpClient();

    public static Headers createAuthenticationHeader(String token) {
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

    public static Request buildPostRequest(String url, Map<String, String> values, String token) {
        Headers headers = createAuthenticationHeader(token);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : values.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().headers(headers).url(url).post(requestBody).build();
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
