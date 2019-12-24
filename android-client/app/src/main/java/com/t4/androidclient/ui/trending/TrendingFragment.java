package com.t4.androidclient.ui.trending;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.t4.androidclient.MainScreenActivity;
import  com.t4.androidclient.R;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.ui.home.HomeFragment;
import com.t4.androidclient.ulti.EndlessRecyclerViewScrollListener;
import com.t4.androidclient.ulti.adapter.StreamRecyclerAdapter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import viewModel.StreamViewModel;

public class TrendingFragment extends Fragment {

    private TrendingViewModel trendingViewModel;
    private RecyclerView recyclerView;
    private List<StreamViewModel> listStream = new LinkedList<>();
    private StreamRecyclerAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trending, container, false);

        listStream = new LinkedList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        adapter = new StreamRecyclerAdapter(listStream, getContext());
        recyclerView = root.findViewById(R.id.list_stream);
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
        return root;
    }

    public void loadNextDataFromApi(int offset) {
        int pageSize = 7;
        String requestNextResourceURL = Api.URL_GET_TRENDING_STREAMS + offset + "/" + pageSize;
        GetTrendingStreams task = new GetTrendingStreams(new AsyncResponse() {
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
                ((MainScreenActivity) getActivity()).progressBar.setVisibility(View.GONE);
            }
        });
        task.execute(requestNextResourceURL);
        ((MainScreenActivity) getActivity()).progressBar.setVisibility(View.VISIBLE);
    }

    private class GetTrendingStreams extends AsyncTask<String, Void, String> {
        public AsyncResponse asyncResponse;

        public GetTrendingStreams(AsyncResponse asyncResponse) {
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