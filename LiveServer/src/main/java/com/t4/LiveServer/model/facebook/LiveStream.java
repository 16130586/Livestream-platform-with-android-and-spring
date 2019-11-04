package com.t4.LiveServer.model.facebook;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class LiveStream {
    @JsonAlias({ "id"})
    public String id;
    @JsonAlias({ "stream_url"})
    public String streamUrl;
    @JsonAlias({ "secure_stream_url"})
    public String secureStreamUrl;
    @JsonAlias({ "stream_secondary_urls" })
    public List<String> streamSecondaryUrls;
    @JsonAlias({ "secure_stream_secondary_urls" })
    public List<String> sercureStreamSecondaryUrls;
}
