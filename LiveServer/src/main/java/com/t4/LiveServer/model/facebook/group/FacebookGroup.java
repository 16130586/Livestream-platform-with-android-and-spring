package com.t4.LiveServer.model.facebook.group;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.t4.LiveServer.model.facebook.pagination.Paging;

import java.util.List;

public class FacebookGroup {
    @JsonAlias({"data"})
    public List<Group> data;
    @JsonAlias({"paging"})
    public Paging paging;

    public FacebookGroup() {

    }
}
