package com.t4.androidclient.ui.home;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.t4.androidclient.MainScreenActivity;
import com.t4.androidclient.R;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.ulti.EndlessRecyclerViewScrollListener;
import com.t4.androidclient.ulti.adapter.StreamRecyclerAdapter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import viewModel.StreamViewModel;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private VideoView clip_ads;
    private RecyclerView recyclerView;
    private List<viewModel.StreamViewModel> listStream = new LinkedList<>();
    private StreamRecyclerAdapter adapter;
    private boolean allowRefresh = false;
    private View root;

    @Override
    public void onResume() {
        super.onResume();
        if (allowRefresh) {
            allowRefresh = false;
            setUp();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!allowRefresh) {
            allowRefresh = true;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
//        clip_ads = root.findViewById(R.id.clip_ads);
//        Uri videoUrl = Uri.parse("android.resource://" + getActivity().getApplicationContext().getPackageName() + "/" + R.raw.clip_ads_example);
//        clip_ads.setVideoURI(videoUrl);
//        clip_ads.requestFocus();
//        clip_ads.start();
        setUp();

        return root;
    }

    public void setUp() {
        listStream = new LinkedList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

        adapter = new StreamRecyclerAdapter(listStream, getContext());
        recyclerView = (RecyclerView) root.findViewById(R.id.list_stream);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        // default loading for recommended
//        loadNextDataFromApi(0);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        scrollListener.onLoadMore(0 , 0 , recyclerView);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //clip_ads.start();
            }
        });
    }

    public void loadNextDataFromApi(int offset) {
        ((MainScreenActivity) getActivity()).progressBar.setVisibility(View.VISIBLE);
        int pageSize = 7;
        String requestNextResourceURL = Api.URL_GET_RECOMMEND_COOKIE_USER + "/" + offset + "/" + pageSize;
        GetNextStreamsTask task = new GetNextStreamsTask(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output == null) return;
                try {
                    ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                    if (response != null && response.statusCode == 200) {
                        List<Map<String, Object>> streams = (List<Map<String, Object>>) response.data;
                        for (Map<String, Object> obj : streams) {
                            StreamViewModel smv = JsonHelper.deserialize(obj , StreamViewModel.class);
                            if (smv == null || smv.getIsFlagged() != -1) continue;
                            listStream.add(smv);
                        }
                        if (streams != null && streams.size() > 0)
                            adapter.notifyItemRangeChanged(offset > 0 ? (offset * pageSize - 1) : 0, streams.size());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((MainScreenActivity) getActivity()).progressBar.setVisibility(View.GONE);
            }
        });
        task.execute(requestNextResourceURL);
    }

    private class GetNextStreamsTask extends AsyncTask<String, Void, String> {
        public AsyncResponse asyncResponse;

        public GetNextStreamsTask(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... urls) {
            if (urls.length <= 0)
                return null;
            String url = urls[0];
            Request request = HttpClient.buildGetRequest(url);
            String body = HttpClient.execute(request);
            return body;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (asyncResponse != null)
                asyncResponse.processFinish(s);
        }
    }
}