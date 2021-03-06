package com.t4.androidclient.contraints;

public class Api {
    public static final String URL_GET_ALL_GENRE = Host.API_HOST_IP + "/streams/genre";
    public static final String URL_GET_INFO = Host.API_HOST_IP + "/user/auth/info";
    public static final String URL_GET_INFO_BY_ID = Host.API_HOST_IP + "/user/info";
    public static final String URL_LOGIN = Host.API_HOST_IP + "/user/login";
    public static final String URL_REGISTER = Host.API_HOST_IP + "/user/registry";
    public static final String URL_GET_RECOMMEND_COOKIE_USER = Host.API_HOST_IP + "/streams/recommend";
    public static final String URL_GET_SUBSCRIPTION_COOKIE_USER = Host.API_HOST_IP + "/user/auth/subscription";
    public static final String URL_DELETE_NOTIFICATION = Host.API_HOST_IP + "/user/auth/notification/delete";
    public static final String URL_GET_NOTIFICATION = Host.API_HOST_IP + "/user/auth/notification";
    public static final String URL_GET_STREAM_TYPES_BY_ID = Host.API_HOST_IP + "/streamtype/listByUserID";
    public static final String URL_CREATE_LIVES_TREAM = Host.API_HOST_IP + "/streams/auth/create";
    public static final String URL_SEARCH_STREAM = Host.API_HOST_IP + "/streams/find";
    public static final String URL_POST_COMMENT = Host.API_HOST_IP + "/streams/auth";
    public static final String URL_SEARCH_STREAM_ADVANCE = Host.API_HOST_IP + "/streams/findAdvance/";
    public static final String URL_CHANNEL_SUBSCRIBE = Host.API_HOST_IP + "/channel/subscribe";
    public static final String URL_CHANNEL_UNSUBSCRIBE = Host.API_HOST_IP + "/channel/unsubscribe";
    public static final String URL_CHANNEL_CHECK_SUBSCRIBE = Host.API_HOST_IP + "/channel/checksubscribe";
    public static final String URL_CHANNEL_GET_STREAMS_BY_TYPE = Host.API_HOST_IP + "/streams/listByUserType";
    public static final String URL_UPDATE_ABOUT = Host.API_HOST_IP + "/user/about/update";
    public static final String URL_GET_WATCHED_STREAMS = Host.API_HOST_IP + "/streams/listWatchedByUserID";
    public static final String URL_GET_SUBSCRIBED_CHANNELS = Host.API_HOST_IP + "/user/listSubscribedByUserID";
    public static final String URL_START_A_LIVE_STREAM = Host.API_HOST_IP + "/streams/auth/{id}/start";
    public static final String URL_STOP_A_LIVE_STREAM = Host.API_HOST_IP + "/streams/auth/{id}/stop";
    public static final String URL_GET_COMMENT_BY_VIDEO_TIME = Host.API_HOST_IP + "/streams/{streamId}/comments/{videoTime}";
    public static final String URL_GET_TRENDING_STREAMS = Host.API_HOST_IP + "/streams/trend/";
    public static final String URL_UP_VIEW = Host.API_HOST_IP + "/streams/upView/";
    public static final String URL_GET_TOP_RANKING = Host.API_HOST_IP + "/ranks/top";
    public static final String URL_GET_LIKE_STATUS = Host.API_HOST_IP + "/streams/auth/like/";
    public static final String URL_REPORT_LIVE = Host.API_HOST_IP + "/streams/auth/report/";


}
