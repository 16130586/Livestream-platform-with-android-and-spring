package com.t4.LiveServer.model.wowza;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.t4.LiveServer.config.WowzaConfig;
import org.springframework.http.*;

public class WowzaStream {
    public static final String URL_LIVE_STREAM="https://api.cloud.wowza.com/api/v1.3/live_streams";
    public static final String URL_STREAM_TARGETS ="https://api.cloud.wowza.com/api/v1.3/stream_targets";

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
}
