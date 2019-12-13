package com.t4.LiveServer.entryParam.base.Stream;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@Data
public class CreatingStreamEntryParams {
    public String name, thumbnail = null;
    public int isStored;
    public List<StreamingForward> forwards;
}
