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
import com.t4.androidclient.adapter.ChannelTypeAdapter;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.model.helper.StreamTypeHelper;
import com.t4.androidclient.model.livestream.StreamType;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import viewModel.StreamTypeViewModel;
import viewModel.StreamViewModel;

public class TypeStreamsFragment extends Fragment {
    int ownerID;
    private ChannelTypeAdapter channelTypeAdapter;
    private RecyclerView recyclerView;
    private List<StreamTypeViewModel> listStreamAndTypeView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_type_streams, container, false);
        ownerID = getActivity().getIntent().getIntExtra("owner_id",-1);
        listStreamAndTypeView = new ArrayList<>();

        TypeStreamsFragment.StreamTypes streamTypes = new TypeStreamsFragment.StreamTypes(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                        List<StreamType> listStreamType = new ArrayList<StreamType>();
                        StreamTypeHelper streamTypeHelper = new StreamTypeHelper();
                        listStreamType = streamTypeHelper.parseGenreJson(output);
                        for(StreamType type : listStreamType){
                            StreamTypeViewModel typeView =  new StreamTypeViewModel();
                                typeView.setId(type.getTypeId());
                                typeView.setTypeName(type.getTypeName());
                                typeView.setNumberOfType(type.getNumberOfType());
                                listStreamAndTypeView.add(typeView);
                        }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        //Save ID / Channel Name into new Activity through Adapter
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(ownerID+""); // ID
                    dataList.add(getActivity().getIntent().getStringExtra("channel_name")); // NAME
                    channelTypeAdapter = new ChannelTypeAdapter(listStreamAndTypeView, getContext(),dataList);
                    recyclerView = (RecyclerView) root.findViewById(R.id.list_type_stream);
                    recyclerView.setAdapter(channelTypeAdapter);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setClickable(true);
                }
                catch (Exception o) {
                    o.printStackTrace();
                }
            }});

        String[] values = new String[2];
        values[0] = "userID";
        values[1] = String.valueOf(ownerID);
        streamTypes.execute(values);
        return root;
    }


    // ============ DO GET STREAM TYPE OF USER  ==================================
    private class StreamTypes extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public StreamTypes(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... token) {
            Request request = HttpClient.buildGetRequest(Api.URL_GET_STREAM_TYPES_BY_ID+"/"+token[1]);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }
}