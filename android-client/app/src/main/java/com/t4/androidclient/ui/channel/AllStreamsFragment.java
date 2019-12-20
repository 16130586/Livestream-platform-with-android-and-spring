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
import com.t4.androidclient.contraints.Host;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.model.helper.LiveStreamHelper;
import com.t4.androidclient.model.livestream.LiveStream;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import viewModel.StreamViewModel;

public class AllStreamsFragment extends Fragment {
    private View root;
    private ChannelStreamAdapter channelStreamAdapter;
    private RecyclerView recyclerView;
    private List<StreamViewModel> listStreamView;
    private int ownerID;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_all_streams, container, false);
        ownerID = getActivity().getIntent().getIntExtra("DATA",-1);
        setUp();
        return root;
    }

    public void setUp() {
        listStreamView = new LinkedList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        channelStreamAdapter = new ChannelStreamAdapter(listStreamView, getContext());
        recyclerView = (RecyclerView) root.findViewById(R.id.list_all_streams);
        recyclerView.setAdapter(channelStreamAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadUserStreamsFrom(1);
    }


    public void loadUserStreamsFrom(int offset) {
        int pageSize = 6;
        GetListStream getListStream = new GetListStream(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output == null) return;
                try {
                    ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                    if (response != null && response.statusCode == 200) {
                        List<Map<String, Object>> streams = (List<Map<String, Object>>) response.data;
                        for (Map<String, Object> obj : streams) {
                            LiveStream liveStream = LiveStreamHelper.parse(obj);
                            if (liveStream.getOwner().getId() == ownerID) {
                                StreamViewModel streamView = new StreamViewModel(liveStream.getStreamId()
                                        , liveStream.getName()
                                        , (liveStream.getEndTime() != null ? liveStream.getEndTime() : new Date(1573837200)) //16/11/2019
                                        , (liveStream.getTotalView() != null ? liveStream.getTotalView() : 69069)
                                        , (liveStream.getThumbnail() != null ? liveStream.getThumbnail() : ""));
                                listStreamView.add(streamView);
                            } else if (liveStream == null) {
                                continue;
                            }
                        }
                        if (streams != null && streams.size() > 0)
                            channelStreamAdapter.notifyItemRangeChanged(offset > 0 ? (offset * pageSize - 1) : 0, streams.size());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } });
        String[] values = new String[6];
        values[0] = "userID";
        values[1] = String.valueOf(ownerID);
        values[2] = "offset";
        values[3] = ""+offset ;
        values[4] = "limit";
        values[5] = ""+pageSize ;
        getListStream.execute(values);
    }
            private class GetListStream extends AsyncTask<String, Integer, String> {
                public AsyncResponse asyncResponse;

                public GetListStream(AsyncResponse asyncResponse) {
                    this.asyncResponse = asyncResponse;
                }

                @Override
                protected String doInBackground(String... token) {
                    Request request = HttpClient.buildGetRequest(Host.API_HOST_IP+"/user/"+token[1]+"/streams/"+token[3]+"/"+token[5]);
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