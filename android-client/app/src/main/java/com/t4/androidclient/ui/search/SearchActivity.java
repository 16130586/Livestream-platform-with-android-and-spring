package com.t4.androidclient.ui.search;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.t4.androidclient.MainScreenActivity;
import com.t4.androidclient.R;
import com.t4.androidclient.adapter.CreateLiveGenreAdapter;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Authentication;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.model.helper.GenreHelper;
import com.t4.androidclient.ulti.EndlessRecyclerViewScrollListener;
import com.t4.androidclient.ulti.adapter.StreamRecyclerAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import viewModel.StreamViewModel;

public class SearchActivity extends Activity {
    FloatingSearchView mSearchView;
    ImageView app_logo;
    private RecyclerView recyclerView;
    private List<StreamViewModel> listStream = new LinkedList<>();
    private List<String> genreList, genreListByUser = new ArrayList<>();
    private StreamRecyclerAdapter adapter;
    private String keywords ;
    private GridView gridView;
    private String primaryUrl = Api.URL_SEARCH_STREAM;
    private String primaryUrlAdvance = Api.URL_SEARCH_STREAM_ADVANCE;
    ProgressBar progressBar;
    private Adapter genreAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        progressBar = findViewById(R.id.search_loading);
        gridView = findViewById(R.id.search_advance_genre);
        getGenreListFromServer();

        keywords = getIntent().getStringExtra("keywords");
        mSearchView  = findViewById(R.id.floating_search_view);
        if (keywords != null && !keywords.isEmpty()) {
            mSearchView.setSearchText(keywords);
            keywords += "/";
        }

        // Click back về Home
        app_logo = findViewById(R.id.back_logo);
        app_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
                Intent backHome = new Intent(SearchActivity.this, MainScreenActivity.class);
                startActivity(backHome);
                overridePendingTransition(0, 0);
            }
        });

        listStream = new LinkedList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new StreamRecyclerAdapter(listStream, this);
        recyclerView = (RecyclerView) this.findViewById(R.id.list_stream);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
        if (keywords != null && !keywords.isEmpty())
            scrollListener.onLoadMore(0 , 0 , recyclerView);

        FloatingSearchView.OnSearchListener onSearchListener = new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
            }

            @Override
            public void onSearchAction(String currentQuery) {
                adapter.clear();
                keywords = (currentQuery != null && !currentQuery.isEmpty()) ? currentQuery + "/" : "";
                scrollListener.resetState();
                scrollListener.onLoadMore(0 , 0 , recyclerView);
            }
        };

        mSearchView.setOnSearchListener(onSearchListener);
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.search_advance) {
                    gridView.setVisibility(gridView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                } else if (item.getItemId() == R.id.search_btn) {
                    onSearchListener.onSearchAction(mSearchView.getQuery());
                }
            }
        });
    }
    public void loadNextDataFromApi(int offset) {
        progressBar.setVisibility(View.VISIBLE);
        int pageSize = 7;
        String requestNextResourceURL = "";
        if (genreListByUser.isEmpty()) {
            requestNextResourceURL = primaryUrl + ((keywords != null && !keywords.isEmpty()) ? keywords : "") + offset + "/" + pageSize;
            SearchStream task = new SearchStream(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    if (output == null) return;
                    try {
                        ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                        if (response != null && response.statusCode == 200) {
                            List<Map<String, Object>> streams = (List<Map<String, Object>>) response.data;
                            for (Map<String, Object> obj : streams) {
                                StreamViewModel smv = JsonHelper.deserialize(obj , StreamViewModel.class);
                                if (smv == null) continue;
                                listStream.add(smv);
                            }
                            if (streams != null && streams.size() > 0)
                                adapter.notifyItemRangeChanged(offset > 0 ? (offset * pageSize - 1) : 0, streams.size());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
            task.execute(requestNextResourceURL);
        }
        else {
            requestNextResourceURL = primaryUrlAdvance + ((keywords != null && !keywords.isEmpty()) ? keywords : "") + offset + "/" + pageSize;
            SearchStreamAdvance task = new SearchStreamAdvance(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    if (output == null) return;
                    try {
                        ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                        if (response != null && response.statusCode == 200) {
                            List<Map<String, Object>> streams = (List<Map<String, Object>>) response.data;
                            for (Map<String, Object> obj : streams) {
                                StreamViewModel smv = JsonHelper.deserialize(obj , StreamViewModel.class);
                                if (smv == null) continue;
                                listStream.add(smv);
                            }
                            if (streams != null && streams.size() > 0)
                                adapter.notifyItemRangeChanged(offset > 0 ? (offset * pageSize - 1) : 0, streams.size());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
            task.execute(requestNextResourceURL);
        }
    }

// =======================================================================
// ============ SEARCH STREAM ==================================
// AsyncTask to search stream
    private class SearchStream extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public SearchStream(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... input) {
            Request request = HttpClient.buildGetRequest(input[0]);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }

    // AsyncTask to search stream advance
    private class SearchStreamAdvance extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public SearchStreamAdvance(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... input) {
            Request request = HttpClient.buildPostRequest(input[0], genreListByUser);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }
// ============ END SEARCH STREAM ==================================
// =======================================================================

    private void getGenreListFromServer() {
        // server
        GetGenreList getGenreList = new GetGenreList(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                GenreHelper helper = new GenreHelper();
                List<String> genreListServer = helper.parseGenreJson(output);
                genreList = genreListServer;
                genreAdapter = new CreateLiveGenreAdapter(SearchActivity.this, genreList);
                gridView.setAdapter((ListAdapter) genreAdapter);
            }
        });
        getGenreList.execute();

    }

    public void onCheckboxClicked(View view) {
        CheckBox checkBox = (CheckBox) view;
        boolean checked = checkBox.isChecked();
        if (checked) {
            genreListByUser.add(checkBox.getText().toString());
        } else {
            genreListByUser.remove(checkBox.getText().toString());
        }
    }

    // AsyncTask to get the genre list
    private class GetGenreList extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public GetGenreList(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... urls) {
            Request request = HttpClient.buildGetRequest(Api.URL_GET_ALL_GENRE);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }
}
