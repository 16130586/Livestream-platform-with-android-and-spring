package com.t4.LiveServer.core;


import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

public class MagicHttpClient {
    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public MagicHttpClient() {
    }

    public String getQueryFromObjectEntryParams(Object entryParams) throws InstantiationException, IllegalAccessException {
        if (entryParams == null) {
            return "";
        } else {
            String query = "";
            Field[] fields = entryParams.getClass().getDeclaredFields();
            if (fields.length < 1) {
                return "";
            } else {
                for (int i = 0; i < fields.length; ++i) {
                    Field field = fields[i];
                    if (i == 0) {
                        query = query + "?";
                    }

                    if (!"PRIVATE".startsWith(Modifier.toString(field.getModifiers()).toUpperCase())) {
                        field.setAccessible(true);

                        try {
                            Object fieldValue = field.get(entryParams);
                            if (!isDefault(fieldValue , field)) {//!fieldValue.getClass().newInstance().equals(fieldValue)) {
                                query = query + field.getName() + "=" + field.get(entryParams).toString();
                                if (i != fields.length - 1) {
                                    query = query + "&";
                                }
                            }
                        } catch (Exception var7) {
                            var7.printStackTrace();
                            throw var7;
                        }
                    }
                    else if("&".equals(query.substring(query.length()-1))) {
                        query = query.substring(0 , query.length()-1);
                    }
                }

                return query;
            }
        }
    }

    public ApiResponse get(Map<String, String> headers, String baseUrl, String action, Object queryEntryParams) throws IOException, IllegalAccessException, InstantiationException {
        if (action == null) {
            action = "";
        }

        String fullyUrl = "";

        try {
            fullyUrl = baseUrl + action + this.getQueryFromObjectEntryParams(queryEntryParams);
        } catch (Exception var23) {
            throw var23;
        }

        Request.Builder builder = (new Request.Builder()).url(fullyUrl);
        if (headers != null) {
            builder = builder.headers(Headers.of(headers));
        }

        Request request = builder.get().build();
        ApiResponse responseReturn = new ApiResponse();

        try {
            Response response = httpClient.newCall(request).execute();
            Throwable var10 = null;

            ApiResponse var11;
            try {
                responseReturn.statusCode = response.code();
                responseReturn.message = response.message();
                responseReturn.data = response.body() != null ? response.body().string() : null;
                var11 = responseReturn;
            } catch (Throwable var22) {
                var10 = var22;
                throw var22;
            } finally {
                if (response != null) {
                    if (var10 != null) {
                        try {
                            response.close();
                        } catch (Throwable var21) {
                            var10.addSuppressed(var21);
                        }
                    } else {
                        response.close();
                    }
                }

            }

            return var11;
        } catch (IOException var25) {
            var25.printStackTrace();
            throw var25;
        }
    }

    public ApiResponse post(Map<String, String> headers, String baseUrl, String action, String jsonStringData) throws Exception {
        if (action == null) {
            action = "";
        }

        if (jsonStringData == null) {
            jsonStringData = "";
        }

        String fullyUrl = baseUrl + action;
        Request.Builder builder = (new Request.Builder()).url(fullyUrl);
        if (headers != null) {
            builder = builder.headers(Headers.of(headers));
        }

        Request request = builder.post(RequestBody.create(jsonStringData, MEDIA_TYPE_JSON)).build();
        ApiResponse responseReturn = new ApiResponse();

        try {
            Response response = httpClient.newCall(request).execute();
            Throwable var10 = null;

            ApiResponse var11;
            try {
                responseReturn.statusCode = response.code();
                responseReturn.message = response.message();
                responseReturn.data = response.body() != null ? response.body().string() : null;
                var11 = responseReturn;
            } catch (Throwable var21) {
                var10 = var21;
                throw var21;
            } finally {
                if (response != null) {
                    if (var10 != null) {
                        try {
                            response.close();
                        } catch (Throwable var20) {
                            var10.addSuppressed(var20);
                        }
                    } else {
                        response.close();
                    }
                }

            }

            return var11;
        } catch (IOException var23) {
            var23.printStackTrace();
            throw var23;
        }
    }

    public ApiResponse put(Map<String, String> headers, String baseUrl, String action, String jsonStringData) throws Exception {
        if (action == null) {
            action = "";
        }

        if (jsonStringData == null) {
            jsonStringData = "";
        }

        String fullyUrl = baseUrl + action;
        Request.Builder builder = (new Request.Builder()).url(fullyUrl);
        if (headers != null) {
            builder = builder.headers(Headers.of(headers));
        }

        Request request = builder.put(RequestBody.create(jsonStringData, MEDIA_TYPE_JSON)).build();
        ApiResponse responseReturn = new ApiResponse();

        try {
            Response response = httpClient.newCall(request).execute();
            Throwable var10 = null;

            ApiResponse var11;
            try {
                responseReturn.statusCode = response.code();
                responseReturn.message = response.message();
                responseReturn.data = response.body() != null ? response.body().string() : null;
                var11 = responseReturn;
            } catch (Throwable var21) {
                var10 = var21;
                throw var21;
            } finally {
                if (response != null) {
                    if (var10 != null) {
                        try {
                            response.close();
                        } catch (Throwable var20) {
                            var10.addSuppressed(var20);
                        }
                    } else {
                        response.close();
                    }
                }

            }

            return var11;
        } catch (IOException var23) {
            var23.printStackTrace();
            throw var23;
        }
    }

    public ApiResponse patch(Map<String, String> headers, String baseUrl, String action, String jsonStringData) throws Exception {
        if (action == null) {
            action = "";
        }

        if (jsonStringData == null) {
            jsonStringData = "";
        }

        String fullyUrl = baseUrl + action;
        Request.Builder builder = (new Request.Builder()).url(fullyUrl);
        if (headers != null) {
            builder = builder.headers(Headers.of(headers));
        }

        Request request = builder.patch(RequestBody.create(jsonStringData, MEDIA_TYPE_JSON)).build();
        ApiResponse responseReturn = new ApiResponse();

        try {
            Response response = httpClient.newCall(request).execute();
            Throwable var10 = null;

            ApiResponse var11;
            try {
                responseReturn.statusCode = response.code();
                responseReturn.message = response.message();
                responseReturn.data = response.body() != null ? response.body().string() : null;
                var11 = responseReturn;
            } catch (Throwable var21) {
                var10 = var21;
                throw var21;
            } finally {
                if (response != null) {
                    if (var10 != null) {
                        try {
                            response.close();
                        } catch (Throwable var20) {
                            var10.addSuppressed(var20);
                        }
                    } else {
                        response.close();
                    }
                }

            }

            return var11;
        } catch (IOException var23) {
            var23.printStackTrace();
            throw var23;
        }
    }

    public ApiResponse delete(Map<String, String> headers, String baseUrl, String action, String jsonStringData) throws Exception {
        if (action == null) {
            action = "";
        }

        if (jsonStringData == null) {
            jsonStringData = "";
        }

        String fullyUrl = baseUrl + action;
        Request.Builder builder = (new Request.Builder()).url(fullyUrl);
        if (headers != null) {
            builder = builder.headers(Headers.of(headers));
        }

        Request request = builder.patch(RequestBody.create(jsonStringData, MEDIA_TYPE_JSON)).build();
        ApiResponse responseReturn = new ApiResponse();

        try {
            Response response = httpClient.newCall(request).execute();
            Throwable var10 = null;

            ApiResponse var11;
            try {
                responseReturn.statusCode = response.code();
                responseReturn.message = response.message();
                responseReturn.data = response.body() != null ? response.body().string() : null;
                var11 = responseReturn;
            } catch (Throwable var21) {
                var10 = var21;
                throw var21;
            } finally {
                if (response != null) {
                    if (var10 != null) {
                        try {
                            response.close();
                        } catch (Throwable var20) {
                            var10.addSuppressed(var20);
                        }
                    } else {
                        response.close();
                    }
                }

            }

            return var11;
        } catch (IOException var23) {
            var23.printStackTrace();
            throw var23;
        }
    }

    public boolean isDefault(Object fieldValue, Field field) {
        try {
            boolean isDefault = false;
            Class type = field.getType();
            Object value = fieldValue;
            if (type == boolean.class && Boolean.FALSE.equals(value)) {
                isDefault = true;
            }
            // found default value
            else if (type == char.class && ((Character) value).charValue() == 0) {
                isDefault = true;
            }
            else if (type == String.class && (value == null)){
                isDefault = true;
            }
            // found default value
            else if (type.isPrimitive() && ((Number) value).doubleValue() == 0) {
                isDefault = true;
            }
            // found default value
            else if (type == null){
                isDefault = true;
            }
            return isDefault;
        } catch (Exception e) {
            return false;
        }
    }
}