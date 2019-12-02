package com.t4.androidclient.model.livestream.page;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.t4.androidclient.model.livestream.pagination.Paging;

import java.util.List;

public class FacebookPage {
    @JsonAlias({"data"})
    public List<Page> data;
    @JsonAlias({"paging"})
    public Paging paging;

    public FacebookPage() {

    }



}