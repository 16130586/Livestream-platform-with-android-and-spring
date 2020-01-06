package com.t4.androidclient.ui.subscription;

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

import  com.t4.androidclient.R;

import com.t4.androidclient.adapter.SubscriptionAdapter;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Authentication;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.model.helper.UserHelper;
import com.t4.androidclient.model.livestream.User;
import com.t4.androidclient.ulti.EndlessRecyclerViewScrollListener;

import java.util.LinkedList;
import java.util.List;

import okhttp3.Request;

public class SubscriptionFragment extends Fragment {

    private SubscriptionViewModel subscriptionViewModel;
    private RecyclerView recyclerView;
    private List<User> userList = new LinkedList<>();
    private SubscriptionAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        subscriptionViewModel = ViewModelProviders.of(this).get(SubscriptionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_subscription, container, false);
        final TextView textView = root.findViewById(R.id.text_subscription);
        subscriptionViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        userList = new LinkedList<>();
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        adapter = new SubscriptionAdapter(userList, getContext());

        recyclerView = (RecyclerView) root.findViewById(R.id.list_subscription);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);


        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page , totalItemsCount);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        scrollListener.onLoadMore(0 , userList.size() , recyclerView);
        return root;
    }

    public void loadNextDataFromApi(int offset , int totalItemsCount) {
        System.out.println(offset + " -- " +  totalItemsCount);
        int pageSize = 7;
        String requestNextResourceURL = Api.URL_GET_SUBSCRIPTION_COOKIE_USER + "/2/" + (offset + 1) + "/" + pageSize;
        System.out.println("==== url " + requestNextResourceURL);
        GetSubscription task = new GetSubscription(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output == null) return;
                try {
                    ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                    if (response != null && response.statusCode == 200) {
                        System.out.println(output);
                        List<User> userLists = UserHelper.parseListUserJson(output);
                        userList.addAll(userLists);
                        if (userList != null && userList.size() > 0)
                            adapter.notifyItemRangeChanged(offset > 0 ? (offset * pageSize - 1) : 0, userList.size());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        task.execute(requestNextResourceURL);
    }

    private class GetSubscription extends AsyncTask<String, Void, String> {
        public AsyncResponse asyncResponse;

        public GetSubscription(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... urls) {
            if (urls.length <= 0)
                return null;
            String url = urls[0];
            Request request = HttpClient.buildGetRequest(url, Authentication.TOKEN);
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