package com.t4.LiveServer.util;


import com.t4.LiveServer.config.WowzaConfig;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.core.MagicHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WowzaApiCaller {
    private WowzaConfig wowzaConfig = new WowzaConfig();
    private MagicHttpClient client = new MagicHttpClient();
    private final String baseUrl = "https://api.cloud.wowza.com/api/";

    public WowzaApiCaller() {
    }

    private Map<String, String> getWowzaConfigHeaders() {
        Map<String, String> headers = new HashMap();
        headers.put("wsc-api-key", this.wowzaConfig.getApiKey());
        headers.put("wsc-access-key ", this.wowzaConfig.getAccessKey());
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }

    public ApiResponse get(String action, Object wowzaQueryObject) throws Exception {
        Map headers = this.getWowzaConfigHeaders();

        try {
            ApiResponse response = this.client.get(headers, "https://api.cloud.wowza.com/api/", action, wowzaQueryObject);
            return response;
        } catch (IOException var5) {
            var5.printStackTrace();
            throw var5;
        }
    }

    public ApiResponse otherMethodAsJson(String method, String action, String jsonStringData) throws Exception {
        String[] supportedMethods = new String[]{"POST", "PUT", "DELETE", "PATCH"};
        boolean isInSupported = false;
        String[] var6 = supportedMethods;
        int var7 = supportedMethods.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            String spMethod = var6[var8];
            if (spMethod.equals(method != null ? method.toUpperCase() : "")) {
                isInSupported = true;
                break;
            }
        }

        if (!isInSupported) {
            return null;
        } else {
            method = method.toUpperCase();

            try {
                Map<String, String> headers = this.getWowzaConfigHeaders();
                ApiResponse result = null;
                byte var13 = -1;
                switch (method.hashCode()) {
                    case 79599:
                        if (method.equals("PUT")) {
                            var13 = 1;
                        }
                        break;
                    case 2461856:
                        if (method.equals("POST")) {
                            var13 = 0;
                        }
                        break;
                    case 75900968:
                        if (method.equals("PATCH")) {
                            var13 = 2;
                        }
                        break;
                    case 2012838315:
                        if (method.equals("DELETE")) {
                            var13 = 3;
                        }
                }

                MagicHttpClient var10000;
                switch (var13) {
                    case 0:
                        var10000 = this.client;
                        this.getClass();
                        result = var10000.post(headers, "https://api.cloud.wowza.com/api/", action, jsonStringData);
                        break;
                    case 1:
                        var10000 = this.client;
                        this.getClass();
                        result = var10000.put(headers, "https://api.cloud.wowza.com/api/", action, jsonStringData);
                        break;
                    case 2:
                        var10000 = this.client;
                        this.getClass();
                        result = var10000.patch(headers, "https://api.cloud.wowza.com/api/", action, jsonStringData);
                        break;
                    case 3:
                        var10000 = this.client;
                        this.getClass();
                        result = var10000.delete(headers, "https://api.cloud.wowza.com/api/", action, jsonStringData);
                }

                return result;
            } catch (Exception var10) {
                var10.printStackTrace();
                throw var10;
            }
        }
    }
}