package com.t4.LiveServer.model.wowza;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class DirectPlaybackUrls {
    @JsonProperty("rtmp")
    public List<DetailDirectPlaybackUrl> rtmp;
    @JsonProperty("rtsp")
    public List<DetailDirectPlaybackUrl> rtsp;
    @JsonProperty("wowz")
    public List<DetailDirectPlaybackUrl> wowz;

    public List<DetailDirectPlaybackUrl> getRtmp() {
        return rtmp;
    }

    public void setRtmp(List<DetailDirectPlaybackUrl> rtmp) {
        this.rtmp = rtmp;
    }

    public List<DetailDirectPlaybackUrl> getRtsp() {
        return rtsp;
    }

    public void setRtsp(List<DetailDirectPlaybackUrl> rtsp) {
        this.rtsp = rtsp;
    }

    public List<DetailDirectPlaybackUrl> getWowz() {
        return wowz;
    }

    public void setWowz(List<DetailDirectPlaybackUrl> wowz) {
        this.wowz = wowz;
    }
}
