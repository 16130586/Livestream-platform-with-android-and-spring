package com.t4.androidclient.ui.inbox;

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
import com.t4.androidclient.adapter.NotificationAdapter;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.httpclient.SqliteAuthenticationHelper;
import com.t4.androidclient.model.helper.NotificationHelper;
import com.t4.androidclient.model.livestream.Notification;
import com.t4.androidclient.ulti.EndlessRecyclerViewScrollListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InboxFragment extends Fragment {
    private View root;
    private InboxViewModel inboxViewModel;
    private NotificationAdapter notificationAdapter;
    private RecyclerView recyclerView;
    private List<Notification> listNotification;


    int i = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_inbox, container, false);
        setUp();
        return root;
    }

    public void setUp() {
        getListInbox();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        notificationAdapter = new NotificationAdapter(listNotification, getContext());
        recyclerView = (RecyclerView) root.findViewById(R.id.list_inbox);
        recyclerView.setAdapter(notificationAdapter);
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
        notificationAdapter.notifyDataSetChanged();
    }

    public void loadNextDataFromApi(int offset) {
        addTenToList();
        notificationAdapter.notifyDataSetChanged();
    }

    public void getListInbox() {
        listNotification = new ArrayList<>();
        addTenToList();
        GetListInbox get = new GetListInbox(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                NotificationHelper helper = new NotificationHelper();
                List<Notification> listNotificationServer = helper.parseNotification(output);
                System.out.println(listNotificationServer .get(0).getMessage());
                System.out.println(listNotificationServer .get(0).getStream().getStreamId());
                System.out.println(output);
                listNotification.addAll(listNotificationServer);
                notificationAdapter.notifyDataSetChanged();
            }
        });
        get.execute("");
    }

    public void addTenToList() {
        int n = 0;
        while(n < 10) {
            n++;
        }
    }

    public void addTenToTop() {
        int n = 0;
        while(n < 10) {
            n++;
        }
    }

    private class GetListInbox extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public GetListInbox(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = "http://192.168.1.4:8080/user/auth/notification";
            SqliteAuthenticationHelper db = new SqliteAuthenticationHelper(getContext());
            String authorization = "Bearer " + db.getToken();

            System.out.println("=============================================================");
            System.out.println("The inbox url: " + url);
            System.out.println("The inbox token: " + db.getToken());
            System.out.println("=============================================================");

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", authorization)
                    .build();

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