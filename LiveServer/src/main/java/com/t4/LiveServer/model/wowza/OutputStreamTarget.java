package com.t4.LiveServer.model.wowza;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.NoArgsConstructor;
@NoArgsConstructor
public class OutputStreamTarget {
    @JsonAlias({"output_id"})
    public String outputId;
    @JsonAlias({"stream_target"})
    public StreamTarget streamTarget;
    @JsonAlias({"stream_target_id"})
    public String streamTargetId;
    @JsonAlias({"id"})
    public String id;
    @JsonAlias({"use_stream_target_backup_url"})
    public boolean useStreamTargetBackupUrl;

    public String getOutputId() {
        return outputId;
    }

    public void setOutputId(String outputId) {
        this.outputId = outputId;
    }

    public StreamTarget getStreamTarget() {
        return streamTarget;
    }

    public void setStreamTarget(StreamTarget streamTarget) {
        this.streamTarget = streamTarget;
    }

    public String getStreamTargetId() {
        return streamTargetId;
    }

    public void setStreamTargetId(String streamTargetId) {
        this.streamTargetId = streamTargetId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isUseStreamTargetBackupUrl() {
        return useStreamTargetBackupUrl;
    }

    public void setUseStreamTargetBackupUrl(boolean useStreamTargetBackupUrl) {
        this.useStreamTargetBackupUrl = useStreamTargetBackupUrl;
    }
}
