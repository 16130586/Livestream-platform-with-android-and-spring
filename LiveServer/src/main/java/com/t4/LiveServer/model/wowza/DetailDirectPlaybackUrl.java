package com.t4.LiveServer.model.wowza;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DetailDirectPlaybackUrl {
    @JsonProperty("name")
    public String name;
    @JsonProperty("output_id")
    public String outputId;
    @JsonProperty("url")
    public String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutputId() {
        return outputId;
    }

    public void setOutputId(String outputId) {
        this.outputId = outputId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
