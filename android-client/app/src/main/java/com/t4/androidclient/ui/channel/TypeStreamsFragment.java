package com.t4.androidclient.ui.channel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.t4.androidclient.R;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.httpclient.HttpClient;

import okhttp3.Request;

public class TypeStreamsFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_type_streams, container, false);
        return root;
    }

    private class About extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public About(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... token) {
            Request request = HttpClient.buildGetRequest(Api.URL_GET_INFO_BY_ID+"/"+token[1]);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }

    // ============ DO GET STREAM TYPE OF USER  ==================================
    private class StreamTypes extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public StreamTypes(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... token) {
            Request request = HttpClient.buildGetRequest(Api.URL_GET_STREAMTYPES_BY_ID+"/"+token[1]);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }
}