package com.t4.androidclient.model.livestream;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.t4.androidclient.model.livestream.pagination.Paging;

import java.util.List;

    public class FacebookGroup {
        @JsonAlias({"data"})
        public List<Group> data;
        @JsonAlias({"paging"})
        public Paging paging;

        public FacebookGroup() {

        }
    }
