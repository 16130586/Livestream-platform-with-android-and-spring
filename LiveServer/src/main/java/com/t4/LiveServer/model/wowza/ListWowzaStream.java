package com.t4.LiveServer.model.wowza;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ListWowzaStream {
    @JsonAlias({"live_streams"})
    public List<WowzaStream> streams;
    @JsonAlias({"pagination"})
    public Pagination pagination;
}
