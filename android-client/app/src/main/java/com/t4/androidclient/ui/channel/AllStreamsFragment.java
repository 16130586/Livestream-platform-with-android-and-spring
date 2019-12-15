package com.t4.androidclient.ui.channel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.t4.androidclient.R;
import com.t4.androidclient.adapter.ChannelStreamAdapter;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.model.livestream.LiveStream;
import com.t4.androidclient.ulti.EndlessRecyclerViewScrollListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllStreamsFragment extends Fragment {
    private View root;
    private ChannelStreamAdapter channelStreamAdapter;
    private RecyclerView recyclerView;
    private List<LiveStream> listStream;


    int i = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_all_streams, container, false);
        setUp();
        return root;
    }

    public void setUp() {
        getListStream();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        channelStreamAdapter = new ChannelStreamAdapter(listStream, getContext());
        recyclerView = (RecyclerView) root.findViewById(R.id.list_all_streams);
        recyclerView.setAdapter(channelStreamAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        addTenToTop();
        channelStreamAdapter.notifyDataSetChanged();
    }

    public void loadNextDataFromApi(int offset) {
        addTenToList();
        channelStreamAdapter.notifyDataSetChanged();
    }

    public void getListStream() {
        listStream = new ArrayList<>();
        addTenToList();
    }

    public void addTenToList() {

        int n = 0;
        while(n < 10) {
            listStream.add(new LiveStream("Faker has Pentakill in his match at LCK",new Date(1544686121),696969));
            //13/12/2018 - date
            n++;
        }
    }

    public void addTenToTop() {
        int n = 0;
        while(n < 10) {
            //10/12/2018 - date
            listStream.add(0,new LiveStream("New stream - Faker has Pentakill in his match at VCS",new Date(1575962921),696969));
            n++;
        }
    }

    private class GetListStream extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public GetListStream(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = "link to server to get the inbox";

            System.out.println("=============================================================");
            System.out.println("The inbox url: " + url);
            System.out.println("=============================================================");

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                String rs = response.body().string();

                return rs;
            } catch (IOException e) {
                e.printStackTrace();
                return "false";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }
}