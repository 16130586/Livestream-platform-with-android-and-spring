package com.t4.androidclient.core;

public class ApiResponse {
    public int statusCode;
    public int errorCode;
    public String message;
    public Object data;

    public ApiResponse() {
    }

    public ApiResponse(int statusCode, int errorCode, String message, Object dataAsJson) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
        this.data = dataAsJson;
    }
}

