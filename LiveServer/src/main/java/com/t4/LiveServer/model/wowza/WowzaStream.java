package com.t4.LiveServer.model.wowza;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.t4.LiveServer.config.WowzaConfig;
import org.springframework.http.*;

public class WowzaStream {
    public static final String URL_LIVE_STREAM="https://api.cloud.wowza.com/api/v1.3/live_streams";
    public WowzaStream(){

    }
    @JsonProperty("id")
    public String id;
    @JsonProperty("aspect_ratio_height")
    public int height;
    @JsonProperty("aspect_ratio_width")
    public int width;
    @JsonProperty("billing_mode")
    public String billingMode = "pay_as_you_go";
    @JsonProperty("broadcast_location")
    public String broadCastLocation = "asia_pacific_singapore";
    @JsonProperty("closed_caption_type ")
    public String closedCaptionType = "none";
    @JsonProperty("encoder")
    public String encoder = "other_rtmp";
    @JsonProperty("name")
    public String name;
    @JsonProperty("transcoder_type")
    public String transCoderType = "transcoded";
    @JsonProperty("delivery_method")
    public String deliveryMethod = "push";
    @JsonProperty("delivery_protocols")
    public String[] deliveryProtocols = new String[]{"rtmp", "rtsp", "wowz"};
    @JsonProperty("delivery_type")
    public String deliveryType = "single-bitrate";
    @JsonProperty("disable_authentication")
    public boolean isDisableAuthentication = true;
    @JsonProperty("low_latency")
    public boolean isLowLatency = true;
    @JsonProperty("password")
    public String password = "GENRATED_PASSWORD";
    @JsonProperty("player_responsive")
    public boolean isPlayerResponsive = true;
    @JsonProperty("player_type")
    public String playerType = "original_html5";
    @JsonProperty("recording")
    public boolean isRecording = false;
    @JsonProperty("source_url")
    public String sourceUrl;
    @JsonProperty("target_delivery_protocol")
    public String targetDeliveryProtocol = "hls-https";
    @JsonProperty("use_stream_source")
    public boolean isUseStreamSource = false;
    @JsonProperty("username")
    public String userName = "GENRATE_USERNAME";

    public WowzaStream(int height, int width, String billingMode, String broadCastLocation, String encoder, String name, String transCoderType) {
        this.height = height;
        this.width = width;
        this.billingMode = billingMode;
        this.broadCastLocation = broadCastLocation;
        this.encoder = encoder;
        this.name = name;
        this.transCoderType = transCoderType;
    }

    public class CloseCaptionType {
        public static final String NONE = "none";
        public static final String CEA = "cea";
        public static final String ON_TEXT = "on_text";
        public static final String BOTH = "both";

        public CloseCaptionType() {
        }
    }

    public class TargetDeliveryProtocol {
        public static final String HLS_HTTPS = "hls-https";
        public static final String HLS_HDS = "hls-hds";

        public TargetDeliveryProtocol() {
        }
    }

    public class PlayerType {
        public static final String ORIGINAL = "original_html5";
        public static final String WOWZA = "wowza_player";

        public PlayerType() {
        }
    }

    public class LogoPosition {
        public static final String TOP_LEFT = "top-left";
        public static final String TOP_RIGHT = "top-right";
        public static final String BOTTOM_LEFT = "bottom-left";
        public static final String BOTTOM_RIGHT = "bottom-right";

        public LogoPosition() {
        }
    }

    public class DeliveryType {
        public static final String SINGLE_BITRATE = "single-bitrate";
        public static final String MUTIL_BITRATE = "multi-bitrate";

        public DeliveryType() {
        }
    }

    public class DeliveryProtocol {
        public static final String RTMP = "rtmp";
        public static final String RTSP = "rtsp";
        public static final String WOWZA = "wowz";

        public DeliveryProtocol() {
        }
    }

    public class DeliveryMethod {
        public static final String PULL = "pull";
        public static final String PUSH = "push";
        public static final String CDN = "cdn";

        public DeliveryMethod() {
        }
    }

    public class TransCodeType {
        public static final String PASS_THROUGH = "passthrough";
        public static final String TRANS_CODED = "transcoded";

        public TransCodeType() {
        }
    }

    public class Encoder {
        public static final String WOWZA_ENGINE = "wowza_streaming_engine";
        public static final String WOWZA_GO_CODER = "wowza_gocoder";
        public static final String OTHER_RTMP = "other_rtmp";

        public Encoder() {
        }
    }

    public class BroadCastLocation {
        public static final String SINGAPORE = "asia_pacific_singapore";

        public BroadCastLocation() {
        }
    }

    public class BillingMode {
        public static final String PAY_AS_YOU_GO = "pay_as_you_go";
        public static final String T24_7 = "twentyfour_seven";

        public BillingMode() {
        }
    }

    public static HttpHeaders getWowzaConfigHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("wsc-api-key", WowzaConfig.apiKey);
        headers.add("wsc-access-key", WowzaConfig.accessKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getBillingMode() {
        return billingMode;
    }

    public void setBillingMode(String billingMode) {
        this.billingMode = billingMode;
    }

    public String getBroadCastLocation() {
        return broadCastLocation;
    }

    public void setBroadCastLocation(String broadCastLocation) {
        this.broadCastLocation = broadCastLocation;
    }

    public String getClosedCaptionType() {
        return closedCaptionType;
    }

    public void setClosedCaptionType(String closedCaptionType) {
        this.closedCaptionType = closedCaptionType;
    }

    public String getEncoder() {
        return encoder;
    }

    public void setEncoder(String encoder) {
        this.encoder = encoder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransCoderType() {
        return transCoderType;
    }

    public void setTransCoderType(String transCoderType) {
        this.transCoderType = transCoderType;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String[] getDeliveryProtocols() {
        return deliveryProtocols;
    }

    public void setDeliveryProtocols(String[] deliveryProtocols) {
        this.deliveryProtocols = deliveryProtocols;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public boolean isDisableAuthentication() {
        return isDisableAuthentication;
    }

    public void setDisableAuthentication(boolean disableAuthentication) {
        isDisableAuthentication = disableAuthentication;
    }

    public boolean isLowLatency() {
        return isLowLatency;
    }

    public void setLowLatency(boolean lowLatency) {
        isLowLatency = lowLatency;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPlayerResponsive() {
        return isPlayerResponsive;
    }

    public void setPlayerResponsive(boolean playerResponsive) {
        isPlayerResponsive = playerResponsive;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void setRecording(boolean recording) {
        isRecording = recording;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getTargetDeliveryProtocol() {
        return targetDeliveryProtocol;
    }

    public void setTargetDeliveryProtocol(String targetDeliveryProtocol) {
        this.targetDeliveryProtocol = targetDeliveryProtocol;
    }

    public boolean isUseStreamSource() {
        return isUseStreamSource;
    }

    public void setUseStreamSource(boolean useStreamSource) {
        isUseStreamSource = useStreamSource;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
