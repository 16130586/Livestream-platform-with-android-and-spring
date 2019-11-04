package com.t4.LiveServer.entryParam.base.Wowza;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AdditionOutputStreamTargetToTransCoderEntryParam {
    public String streamTargetId;
    public String transCoderId;
    public String outputId;

    public AdditionOutputStreamTargetToTransCoderEntryParam(){

    }
    public AdditionOutputStreamTargetToTransCoderEntryParam(String transCoderId, String outputId, String streamTargetId) {
        this.streamTargetId = streamTargetId;
        this.transCoderId = transCoderId;
        this.outputId = outputId;
    }
}
