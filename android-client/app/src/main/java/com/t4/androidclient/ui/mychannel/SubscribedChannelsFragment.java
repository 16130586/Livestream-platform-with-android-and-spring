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
import com.t4.androidclient.adapter.SubscribedChannelAdapter;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.model.helper.UserHelper;
import com.t4.androidclient.model.livestream.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import viewModel.UserModelView;

public class SubscribedChannelsFragment extends Fragment {
    private View root;
    private SubscribedChannelAdapter subscribedChannelAdapter;
    private RecyclerView recyclerView;
    private List<UserModelView> listUserView;
    private TextView subscribedChannelNumber ;
    private int ownerID;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mychannel_subscribed_channels, container, false);
        ownerID = getActivity().getIntent().getIntExtra("owner_id",-1);
        subscribedChannelNumber = root.findViewById(R.id.subscribed_channel_number);
        setUp();
        return root;
    }

    public void setUp() {
        listUserView = new LinkedList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        subscribedChannelAdapter = new SubscribedChannelAdapter(listUserView, getContext());
        recyclerView = (RecyclerView) root.findViewById(R.id.mychannel_list_subscribed_channel);
        recyclerView.setAdapter(subscribedChannelAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadWatchedStreamsFrom(1);
        }

        public void loadWatchedStreamsFrom(int offset) {
            int pageSize = 6;
            GetListSubscribedChannel getListStream = new GetListSubscribedChannel(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    if (output == null) return;
                    try {
                        ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                        if (response != null && response.statusCode == 200) {
                            List<Map<String, Object>> users = (List<Map<String, Object>>) response.data;
                            if (users != null && users.size() > 0) {
                                for (Map<String, Object> obj : users) {
                                    User user = UserHelper.parseUserJson(obj);
                                    if (user!=null) {
                                        UserModelView userView = new UserModelView();
                                        userView.setUserId(user.getId());
                                        userView.setNickName(user.getNickname());
                                        userView.setAvatar(user.getAvatar()!= null ? user.getAvatar() : "");
                                        userView.setSubscriberTotal(user.getSubscribeTotal() );
                                        listUserView.add(userView);
                                    } else {
                                        continue;
                                    }
                                }
                                subscribedChannelNumber.setText(recyclerView.getAdapter().getItemCount() +" streams");
                                loadWatchedStreamsFrom(offset + pageSize);
                                subscribedChannelAdapter.notifyDataSetChanged();
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

    private class GetListSubscribedChannel extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse;

        public GetListSubscribedChannel(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... token) {
            Request request = HttpClient.buildGetRequest(Api.URL_GET_SUBSCRIBED_CHANNELS+"/"+token[0]+"/"+token[1]+"/"+token[2]);
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