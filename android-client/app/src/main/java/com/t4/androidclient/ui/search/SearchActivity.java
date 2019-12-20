package com.t4.androidclient.ui.search;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.t4.androidclient.MainScreenActivity;
import com.t4.androidclient.R;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.searching.asyn;
import com.t4.androidclient.ui.channel.ChannelActivity;
import com.t4.androidclient.ulti.EndlessRecyclerViewScrollListener;
import com.t4.androidclient.ulti.adapter.StreamRecyclerAdapter;

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
    private StreamRecyclerAdapter adapter;
    private String keywords ;
    private String primaryUrl = Api.URL_SEARCH_STREAM;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        keywords = getIntent().getStringExtra("keywords");
        if (keywords != null && !keywords.isEmpty())
            primaryUrl += keywords + "/";
        mSearchView  = findViewById(R.id.floating_search_view);
        mSearchView.setSearchHint(keywords);

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
        scrollListener.onLoadMore(0 , 0 , recyclerView);

        // cái này là kiểm tra thay đổi trên search
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {
                    mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
                        @Override
                        public void onSearchTextChanged(String oldQuery, String newQuery) {

                        }
                    });

                }
            }
        });
    }
    public void loadNextDataFromApi(int offset) {
        int pageSize = 7;
        String requestNextResourceURL = primaryUrl + offset + "/" + pageSize;
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
            }
        });
        task.execute(requestNextResourceURL);
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
}
