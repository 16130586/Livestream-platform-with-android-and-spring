package com.t4.LiveServer.model.wowza;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Pagination {
    @JsonAlias({"total_records"})
    public int totalRecords;
    @JsonAlias({"page"})
    public int page;
    @JsonAlias({"per_page"})
    public int perPage;
    @JsonAlias({"total_pages"})
    public int totalPage;
    @JsonAlias({"page_first_index"})
    public int pageFirstIndex;
    @JsonAlias({"page_last_index"})
    public int pageLastIndex;
}
