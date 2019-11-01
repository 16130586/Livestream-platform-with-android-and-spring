package com.t4.LiveServer.model.wowza;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ListOutputStreamTarget {
    public List<StreamOutput> outputs;

    public List<StreamOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<StreamOutput> outputs) {
        this.outputs = outputs;
    }
}
