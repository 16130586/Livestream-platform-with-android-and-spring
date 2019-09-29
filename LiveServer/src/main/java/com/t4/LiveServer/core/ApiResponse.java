package com.t4.LiveServer.core;

public class ApiResponse {
    public int statusCode;
    public int errorCode;
    public String message;
    public String dataAsString;

    public ApiResponse() {
    }

    public ApiResponse(int statusCode, int errorCode, String message, String dataAsJson) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
        this.dataAsString = dataAsJson;
    }
}

