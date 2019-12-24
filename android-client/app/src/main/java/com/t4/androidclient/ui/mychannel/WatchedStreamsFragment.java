package com.t4.androidclient.ui.mychannel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.t4.androidclient.R;
import com.t4.androidclient.adapter.ChannelStreamAdapter;
import com.t4.androidclient.adapter.WatchedStreamAdapter;
import com.t4.androidclient.contraints.Api;
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

public class WatchedStreamsFragment extends Fragment {
    private View root;
    private WatchedStreamAdapter watchedStreamAdapter;
    private RecyclerView recyclerView;
    private List<StreamViewModel> listStreamView;
    private TextView watchedStreamNumber ;
    private int ownerID;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mychannel_watched_streams, container, false);
        ownerID = getActivity().getIntent().getIntExtra("owner_id",-1);
        watchedStreamNumber = root.findViewById(R.id.watched_stream_number);
        setUp();
        return root;
    }

    public void setUp() {
        listStreamView = new LinkedList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        watchedStreamAdapter = new WatchedStreamAdapter(listStreamView, getContext());
        recyclerView = (RecyclerView) root.findViewById(R.id.mychannel_list_watched_streams);
        recyclerView.setAdapter(watchedStreamAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadWatchedStreamsFrom(1);
        }

        public void loadWatchedStreamsFrom(int offset) {
            int pageSize = 6;
            GetListStream getListStream = new GetListStream(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    if (output == null) return;
                    try {
                        ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                        if (response != null && response.statusCode == 200) {
                            List<Map<String, Object>> streams = (List<Map<String, Object>>) response.data;
                            if (streams != null && streams.size() > 0) {
                                for (Map<String, Object> obj : streams) {
                                    LiveStream liveStream = LiveStreamHelper.parse(obj);
                                    if (liveStream!=null) {
                                        StreamViewModel streamView = new StreamViewModel();
                                        streamView.setStreamId(liveStream.getStreamId());
                                        streamView.setStreamName(liveStream.getName());
                                        streamView.setOwner(liveStream.getOwner());
                                        streamView.setThumbnail(liveStream.getThumbnail() != null ? liveStream.getThumbnail() : "");
                                        listStreamView.add(streamView);
                                    } else {
                                        continue;
                                    }
                                }
                                watchedStreamNumber.setText(recyclerView.getAdapter().getItemCount() +" streams");
                                loadWatchedStreamsFrom(offset + pageSize);
                                watchedStreamAdapter.notifyDataSetChanged();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            String[] values = new String[3];
            values[0] = String.valueOf(ownerID);
            values[1] = ""+offset ;
            values[2] = ""+pageSize ;
            getListStream.execute(values);
        }

    private class GetListStream extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse;

        public GetListStream(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... token) {
            Request request = HttpClient.buildGetRequest(Api.URL_GET_WATCHED_STREAMS+"/"+token[0]+"/"+token[1]+"/"+token[2]);
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