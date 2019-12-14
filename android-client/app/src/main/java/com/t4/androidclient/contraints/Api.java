package com.t4.androidclient.contraints;

public class Api {
    public static final String API_HOST_IP = "http://192.168.1.4:8080";
    public static final String URL_GET_ALL_GENRE = API_HOST_IP + "/streams/genre";
    public static final String URL_GET_INFO = API_HOST_IP + "/user/auth/info";
    public static final String URL_LOGIN = API_HOST_IP + "/user/login";
    public static final String URL_GET_RECOMMEND_COOKIE_USER = API_HOST_IP + "/streams/recommend";
    public static final String URL_DELETE_NOTIFICATION = API_HOST_IP + "/user/auth/notification/delete";
}
