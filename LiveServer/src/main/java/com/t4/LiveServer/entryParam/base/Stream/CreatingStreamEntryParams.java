package com.t4.LiveServer.entryParam.base.Stream;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@Data
public class CreatingStreamEntryParams {
    public Integer userId;
    public String name, thumbnail = null;
    public int isStored = 1;
    public List<String> genreList;
    public List<StreamingForward> forwards;
}
