package com.t4.LiveServer.model.wowza;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.t4.LiveServer.config.WowzaConfig;
import org.springframework.http.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonRootName("live_stream")
public class WowzaStream {
    @JsonIgnore
    public static final String URL_LIVE_STREAM = "https://api.cloud.wowza.com/api/v1.3/live_streams";
    @JsonIgnore
    public static final String URL_STREAM_TARGETS = "https://api.cloud.wowza.com/api/v1.3/stream_targets";
    @JsonIgnore
    public static final String URL_TRANSCODERS = "https://api.cloud.wowza.com/api/v1.3/transcoders";

    public WowzaStream() {

    }

    @JsonProperty("id")
    public String id;
    @JsonProperty("aspect_ratio_height")
    public int height = 720;
    @JsonProperty("aspect_ratio_width")
    public int width = 1280;
    @JsonProperty("billing_mode")
    public String billingMode = "pay_as_you_go";
    @JsonProperty("broadcast_location")
    public String broadCastLocation = "asia_pacific_singapore";
    @JsonProperty("closed_caption_type ")
    @JsonAlias({"closed_caption_type"})
    public String closedCaptionType = "none";
    @JsonProperty("encoder")
    public String encoder = "other_rtmp";
    @JsonProperty("name")
    public String name = "";
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
    @JsonProperty("created_at")
    public Date createdAt;
    @JsonProperty("updated_at")
    public Date updatedAt;
    @JsonProperty("connection_code")
    public String connectionCode;
    @JsonProperty("connection_code_expires_at")
    public Date connectionCodeExpiresAt;
    @JsonProperty("source_connection_information")
    public SourceConnectionInformation sourceConnectionInformation;
    @JsonProperty("player_id")
    public String playerId;
    @JsonProperty("player_width")
    public Integer playerWidth = 640;
    @JsonProperty("player_countdown")
    public boolean playerCountdown;
    @JsonProperty("player_embed_code")
    public String playerEmbedCode;
    @JsonProperty("player_hls_playback_url")
    public String playerHlsPlaybackUrl;
    @JsonProperty("hosted_page")
    public boolean hostedPage = false;
    @JsonProperty("hosted_page_title")
    public String hostedPageTitle = "";
    @JsonProperty("hosted_page_url")
    public String hostedPageUrl = "";
    @JsonProperty("hosted_page_logo_image_url")
    public String hostedPageLogoUrl = "";
    @JsonProperty("hosted_page_description")
    public String hostedPageDescription = "";
    @JsonProperty("hosted_page_sharing_icons")
    public boolean hostedPageSharingIcons = false;
    @JsonProperty("stream_targets")
    public List<StreamTarget> streamTargets;
    @JsonProperty("direct_playback_urls")
    public DirectPlaybackUrls directPlaybackUrls;
    @JsonProperty("ip_address")
    public String ipAddress;
    @JsonProperty("state")
    public String state;
    @JsonProperty("player_video_poster_image")
    public String posterImage;

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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

    public SourceConnectionInformation getSourceConnectionInformation() {
        return sourceConnectionInformation;
    }

    public void setSourceConnectionInformation(SourceConnectionInformation sourceConnectionInformation) {
        this.sourceConnectionInformation = sourceConnectionInformation;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Integer getPlayerWidth() {
        return playerWidth;
    }

    public void setPlayerWidth(Integer playerWidth) {
        this.playerWidth = playerWidth;
    }

    public boolean isPlayerCountdown() {
        return playerCountdown;
    }

    public void setPlayerCountdown(boolean playerCountdown) {
        this.playerCountdown = playerCountdown;
    }

    public String getPlayerEmbedCode() {
        return playerEmbedCode;
    }

    public void setPlayerEmbedCode(String playerEmbedCode) {
        this.playerEmbedCode = playerEmbedCode;
    }

    public String getPlayerHlsPlaybackUrl() {
        return playerHlsPlaybackUrl;
    }

    public void setPlayerHlsPlaybackUrl(String playerHlsPlaybackUrl) {
        this.playerHlsPlaybackUrl = playerHlsPlaybackUrl;
    }

    public boolean isHostedPage() {
        return hostedPage;
    }

    public void setHostedPage(boolean hostedPage) {
        this.hostedPage = hostedPage;
    }

    public String getHostedPageTitle() {
        return hostedPageTitle;
    }

    public void setHostedPageTitle(String hostedPageTitle) {
        this.hostedPageTitle = hostedPageTitle;
    }

    public String getHostedPageUrl() {
        return hostedPageUrl;
    }

    public void setHostedPageUrl(String hostedPageUrl) {
        this.hostedPageUrl = hostedPageUrl;
    }

    public String getHostedPageLogoUrl() {
        return hostedPageLogoUrl;
    }

    public void setHostedPageLogoUrl(String hostedPageLogoUrl) {
        this.hostedPageLogoUrl = hostedPageLogoUrl;
    }

    public String getHostedPageDescription() {
        return hostedPageDescription;
    }

    public void setHostedPageDescription(String hostedPageDescription) {
        this.hostedPageDescription = hostedPageDescription;
    }

    public boolean isHostedPageSharingIcons() {
        return hostedPageSharingIcons;
    }

    public void setHostedPageSharingIcons(boolean hostedPageSharingIcons) {
        this.hostedPageSharingIcons = hostedPageSharingIcons;
    }

    public List<StreamTarget> getStreamTargets() {
        return streamTargets;
    }

    public void setStreamTargets(List<StreamTarget> streamTargets) {
        this.streamTargets = streamTargets;
    }

    public DirectPlaybackUrls getDirectPlaybackUrls() {
        return directPlaybackUrls;
    }

    public void setDirectPlaybackUrls(DirectPlaybackUrls directPlaybackUrls) {
        this.directPlaybackUrls = directPlaybackUrls;
    }

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
