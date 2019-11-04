package com.t4.LiveServer.model.wowza;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("transcoder")
public class TransCoder {
    public String id;
    @JsonAlias({"billing_mode"})
    public String billingMode;
    @JsonAlias({"broadcast_location"})
    public String broadcastLocation;
    @JsonAlias({"delivery_method"})
    public String deliveryMethod;
    @JsonAlias({"name"})
    public String name;
    @JsonAlias({"protocol"})
    public String protocol;
    @JsonAlias({"transcoder_type"})
    public String transcoderType;
    @JsonAlias({"buffer_size"})
    public int bufferSize;
    @JsonAlias({"closed_caption_type"})
    public String closedCaptionType;
    @JsonAlias({"delivery_protocols"})
    public List<String> deliveryProtocols;
    @JsonAlias({"description"})
    public String description;
    @JsonAlias({"disable_authentication"})
    public boolean disableAuthentication;
    @JsonAlias({"idle_timeout"})
    public int idleTimeout;
    @JsonAlias({"low_latency"})
    public boolean lowLatency;
    @JsonAlias({"password"})
    public String password;
    @JsonAlias({"play_maximum_connections"})
    public int playMaximumConnections;
    @JsonAlias({"recording"})
    public boolean recording;
    @JsonAlias({"source_url"})
    public String sourceUrl;
    @JsonAlias({"stream_extension"})
    public String streamExtension;
    @JsonAlias({"stream_smoother"})
    public boolean streamSmoother;
    @JsonAlias({"stream_source_id"})
    public String streamSourceId;
    @JsonAlias({"suppress_stream_target_start"})
    public boolean suppressStreamTargetStart;
    @JsonAlias({"username"})
    public String username;
    @JsonAlias({"watermark"})
    public boolean watermark;
    @JsonAlias({"watermark_height"})
    public int watermarkHeight;
    @JsonAlias({"watermark_image"})
    public String watermarkImage;
    @JsonAlias({"watermark_opacity"})
    public int watermarkOpacity;
    @JsonAlias({"watermark_position"})
    public String watermarkPosition;
    @JsonAlias({"watermark_width"})
    public int watermarkWidth;
}
