package com.t4.LiveServer.model.wowza;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.t4.LiveServer.model.ForwardStream;

import java.util.Date;

@JsonRootName("stream_target_custom")
public class StreamTarget {
    public static final String PROVIDER_RTMPS = "rtmps";
    public static final String PROVIDER_RTMP = "rtmp";
    @JsonProperty("connection_code")
    public String connectionCode;
    @JsonProperty("connection_code_expires_at")
    public Date connectionCodeExpiresAt;
    @JsonProperty("created_at")
    public Date createAt;
    @JsonProperty("hds_playback_url")
    public String hdsPlayBackUrl;
    @JsonProperty("hls_playback_url")
    public String hlsPlayBackUrl;
    @JsonProperty("id")
    public String id;
    @JsonProperty("location")
    public String location;
    @JsonProperty("name")
    public String name;
    @JsonProperty("password")
    public String password;
    @JsonProperty("primary_url")
    public String primaryUrl;
    @JsonProperty("provider")
    public String provider;
    @JsonProperty("stream_name")
    public String streamName;
    @JsonProperty("type")
    public String type;
    @JsonProperty("updated_at")
    public Date updatedAt;
    @JsonProperty("username")
    public String username;

    private StreamTarget(){

    }

    public StreamTarget(String provider ,String targetName , String primaryUrl , String streamName){
        this.name = targetName;
        this.primaryUrl = primaryUrl;
        this.streamName = streamName;
        this.provider = provider;
    }
    public StreamTarget(ForwardStream fw){
        this.name = fw.streamName;
        this.primaryUrl = fw.primaryUrl;
        this.streamName = fw.forwardType.name();
        this.provider = fw.provider;

    }
    public String getConnectionCode() {
        return connectionCode;
    }

    public void setConnectionCode(String connectionCode) {
        this.connectionCode = connectionCode;
    }

    public Date getConnectionCodeExpiresAt() {
        return connectionCodeExpiresAt;
    }

    public void setConnectionCodeExpiresAt(Date connectionCodeExpiresAt) {
        this.connectionCodeExpiresAt = connectionCodeExpiresAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getHdsPlayBackUrl() {
        return hdsPlayBackUrl;
    }

    public void setHdsPlayBackUrl(String hdsPlayBackUrl) {
        this.hdsPlayBackUrl = hdsPlayBackUrl;
    }

    public String getHlsPlayBackUrl() {
        return hlsPlayBackUrl;
    }

    public void setHlsPlayBackUrl(String hlsPlayBackUrl) {
        this.hlsPlayBackUrl = hlsPlayBackUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrimaryUrl() {
        return primaryUrl;
    }

    public void setPrimaryUrl(String primaryUrl) {
        this.primaryUrl = primaryUrl;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

