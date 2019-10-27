package com.t4.LiveServer.business.interfaze;

public interface StreamBusiness {
    String create();
    String fetchAll();
    String fetchOne(String id);
    String update();
    String delete(String id);
    String start(String id);
    String stop(String id);
    String reset(String id);
    String regenerate(String id);
    String fetchThumbnail(String id);
    String fetchState(String id);
    String fetchMetrics(String id);
    String fetchVersions();
}
