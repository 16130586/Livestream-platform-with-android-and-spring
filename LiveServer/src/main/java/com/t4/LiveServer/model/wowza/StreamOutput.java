package com.t4.LiveServer.model.wowza;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class StreamOutput {
    @JsonProperty("aspect_ratio_height")
    public int aspectRatioHeight;
    @JsonProperty("aspect_ratio_width")
    public int aspectRatioWidth;
    @JsonProperty("bitrate_audio")
    public int bitRateAudio;
    @JsonProperty("bitrate_video")
    public int bitRateVideo;
    @JsonProperty("created_at")
    public Date createAt;
    @JsonProperty("framerate_reduction")
    public String frameRateReduction;
    @JsonProperty("h264_profile")
    public String h264Profile;
    @JsonProperty("id")
    public String id;
    @JsonProperty("keyframes")
    public String keyFrames;
    @JsonProperty("name")
    public String name;
    @JsonProperty("output_stream_targets")
    public List<OutputStreamTarget> outputStreamTargets;
    @JsonProperty("passthrough_audio")
    public boolean passThroughAudio;
    @JsonProperty("passthrough_video")
    public boolean passThroughVideo;
    @JsonProperty("stream_format")
    public String streamFormat;
    @JsonProperty("transcoder_id")
    public String transCoderId;
    @JsonProperty("updated_at")
    public Date updateAt;

    public int getAspectRatioHeight() {
        return aspectRatioHeight;
    }

    public void setAspectRatioHeight(int aspectRatioHeight) {
        this.aspectRatioHeight = aspectRatioHeight;
    }

    public int getAspectRatioWidth() {
        return aspectRatioWidth;
    }

    public void setAspectRatioWidth(int aspectRatioWidth) {
        this.aspectRatioWidth = aspectRatioWidth;
    }

    public int getBitRateAudio() {
        return bitRateAudio;
    }

    public void setBitRateAudio(int bitRateAudio) {
        this.bitRateAudio = bitRateAudio;
    }

    public int getBitRateVideo() {
        return bitRateVideo;
    }

    public void setBitRateVideo(int bitRateVideo) {
        this.bitRateVideo = bitRateVideo;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getFrameRateReduction() {
        return frameRateReduction;
    }

    public void setFrameRateReduction(String frameRateReduction) {
        this.frameRateReduction = frameRateReduction;
    }

    public String getH264Profile() {
        return h264Profile;
    }

    public void setH264Profile(String h264Profile) {
        this.h264Profile = h264Profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyFrames() {
        return keyFrames;
    }

    public void setKeyFrames(String keyFrames) {
        this.keyFrames = keyFrames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OutputStreamTarget> getOutputStreamTargets() {
        return outputStreamTargets;
    }

    public void setOutputStreamTargets(List<OutputStreamTarget> outputStreamTargets) {
        this.outputStreamTargets = outputStreamTargets;
    }

    public boolean isPassThroughAudio() {
        return passThroughAudio;
    }

    public void setPassThroughAudio(boolean passThroughAudio) {
        this.passThroughAudio = passThroughAudio;
    }

    public boolean isPassThroughVideo() {
        return passThroughVideo;
    }

    public void setPassThroughVideo(boolean passThroughVideo) {
        this.passThroughVideo = passThroughVideo;
    }

    public String getStreamFormat() {
        return streamFormat;
    }

    public void setStreamFormat(String streamFormat) {
        this.streamFormat = streamFormat;
    }

    public String getTransCoderId() {
        return transCoderId;
    }

    public void setTransCoderId(String transCoderId) {
        this.transCoderId = transCoderId;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
