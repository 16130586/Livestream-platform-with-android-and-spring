package com.t4.LiveServer.business.interfaze;

public interface StreamBusiness {
    String create();
    String fetchAll();
    String fetchOne();
    String update();
    String delete();
    String start();
    String stop();
    String reset();
    String regenrate();
    String fetchThumbnail();
    String fetchState();
    String fetchMetrics();
    String fetchVersions();
}
