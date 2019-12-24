package com.t4.androidclient.ui.channel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.t4.androidclient.MainScreenActivity;
import com.t4.androidclient.R;
import com.t4.androidclient.adapter.ChannelStreamAdapter;
import com.t4.androidclient.adapter.ChannelTypeAdapter;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Host;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.model.helper.LiveStreamHelper;
import com.t4.androidclient.model.livestream.LiveStream;
import com.t4.androidclient.searching.MakeSuggestion;
import com.t4.androidclient.searching.Suggestion;
import com.t4.androidclient.searching.asyn;
import com.t4.androidclient.ulti.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import viewModel.StreamTypeViewModel;
import viewModel.StreamViewModel;


public class ChannelStreamsOfTypeActivity extends AppCompatActivity implements MakeSuggestion {
    FloatingSearchView mSearchView;
    ImageView app_logo;
    MakeSuggestion makeSuggestion = this;
    private asyn a = null;
    private int ownerId;
    private int typeId;
    private ChannelStreamAdapter channelStreamAdapter;
    private RecyclerView recyclerView;
    private List<StreamViewModel> listStreamView;
    TextView numberOfStream;
    TextView fullName;
    TextView typeName;
    EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_streams_of_type);
        mSearchView  = findViewById(R.id.channel_streams_of_type_floating_search_view);
        numberOfStream = findViewById(R.id.stream_number);
        fullName = findViewById(R.id.channel_name);
        typeName = findViewById(R.id.channel_type_stream_name);
        listStreamView = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        channelStreamAdapter = new ChannelStreamAdapter(listStreamView, getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.list_all_streams_of_type);
        recyclerView.setAdapter(channelStreamAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        bindNavigateData(getIntent());
        loadStreamsFrom(1) ;


            // Click back về Home
        app_logo = findViewById(R.id.channel_streams_of_type_back_to_types_logo);
        app_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
                Intent backHome = new Intent(ChannelStreamsOfTypeActivity.this, ChannelActivity.class);
                backHome.putExtra("owner_id", getIntent().getIntExtra("owner_id", -1));
                backHome.putExtra("channel_name",  getIntent().getStringExtra("channel_name"));
                startActivity(backHome);
                overridePendingTransition(0, 0);
            }
        });

        /////////////  thêm search vào
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {

            }
        });

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
                            if (!oldQuery.equals("") && newQuery.equals("")) {
                                mSearchView.clearSuggestions();
                            } else {
                                mSearchView.showProgress();
                                if (a != null) {
                                    a.cancel(true);
                                }
                                a = (asyn) new asyn(makeSuggestion).execute("http://suggestqueries.google.com/complete/search?output=firefox&hl=vi&q=" + newQuery);

                            }
                        }
                    });

                }
            }
        });

    }

    private void bindNavigateData(Intent previousNavigationData) {
        mSearchView.setSearchHint("Channel "+previousNavigationData.getStringExtra("channel_name"));
        numberOfStream.setText(previousNavigationData.getIntExtra("stream_number",-1)+" streams");
        fullName.setText("Created by "+previousNavigationData.getStringExtra("channel_name"));
        typeName.setText("List of type "+previousNavigationData.getStringExtra("type_name"));
        this.ownerId = previousNavigationData.getIntExtra("owner_id", -1);
        this.typeId = previousNavigationData.getIntExtra("type_id", -1);
    }


    @Override
    public void getSuggestion(List<Suggestion> suggestions) {
        mSearchView.swapSuggestions(suggestions);
        mSearchView.hideProgress();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // kiểm tra kết quả search
        if (requestCode == 0 && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            mSearchView.setFocusable(true);
            mSearchView.setSearchText(results.get(0));
        }
    }

    public void loadStreamsFrom(int offset) {
        int pageSize = 6;
        ChannelStreamsOfTypeActivity.GetListStream getListStream = new ChannelStreamsOfTypeActivity.GetListStream(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output == null) return;
                try {
                    ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                    if (response != null && response.statusCode == 200) {
                        List<Map<String, Object>> streams = (List<Map<String, Object>>) response.data;
                        if (streams != null && streams.size() > 0){
                            for (Map<String, Object> obj : streams) {
                            LiveStream liveStream = LiveStreamHelper.parse(obj);
                            if (liveStream!=null) {
                                StreamViewModel streamView = new StreamViewModel();
                                streamView.setStreamId(liveStream.getStreamId());
                                streamView.setStreamName(liveStream.getName());
                                streamView.setEndTime(liveStream.getEndTime() != null ? liveStream.getEndTime() : new Date(1573837200)); //16/11/2019
                                streamView.setTotalView(liveStream.getTotalView() != null ? liveStream.getTotalView() : 69069);
                                streamView.setThumbnail(liveStream.getThumbnail() != null ? liveStream.getThumbnail() : "");
                                listStreamView.add(streamView);
                            } else  {
                                continue;
                            }
                        }
                            loadStreamsFrom(offset+pageSize) ;
                            channelStreamAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        String[] values = new String[4];
        values[0] = String.valueOf(ownerId);
        values[1] = String.valueOf(typeId);
        values[2] = ""+offset ;
        values[3] = ""+pageSize ;
        getListStream.execute(values);
    }

    private class GetListStream extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse;

        public GetListStream(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... token) {
            Request request = HttpClient.buildGetRequest(Api.URL_CHANNEL_GET_STREAMS_BY_TYPE+"/"+token[0]+"/"+token[1]+"/"+token[2]+"/"+token[3]);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (asyncResponse != null)
                asyncResponse.processFinish(s);
        }
    }
}
