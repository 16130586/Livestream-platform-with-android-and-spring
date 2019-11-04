package com.t4.LiveServer.model.wowza;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SourceConnectionInformation {
    @JsonProperty("primary_server")
    public String primaryServer;
    @JsonProperty("host_port")
    public Integer hostPort;
    @JsonProperty("application")
    public String application;
    @JsonProperty("stream_name")
    public String streamName;
    @JsonProperty("disable_authentication")
    public boolean disableAuthentication;
    @JsonProperty("username")
    public String username;
    @JsonProperty("password")
    public String password;

    public String getPrimaryServer() {
        return primaryServer;
    }

    public void setPrimaryServer(String primaryServer) {
        this.primaryServer = primaryServer;
    }

    public Integer getHostPort() {
        return hostPort;
    }

    public void setHostPort(Integer hostPort) {
        this.hostPort = hostPort;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public boolean isDisableAuthentication() {
        return disableAuthentication;
    }

    public void setDisableAuthentication(boolean disableAuthentication) {
        this.disableAuthentication = disableAuthentication;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
