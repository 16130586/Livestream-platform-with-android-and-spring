package com.t4.LiveServer.entryParam.base.Stream;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@Data
public class CreatingStreamEntryParams {
    public int isStored;
    public List<StreamingForward> forwards;

}
