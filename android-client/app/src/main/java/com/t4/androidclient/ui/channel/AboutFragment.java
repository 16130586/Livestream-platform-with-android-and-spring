package com.t4.androidclient.ui.channel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.t4.androidclient.MainScreenActivity;
import com.t4.androidclient.R;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Host;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.httpclient.SqliteAuthenticationHelper;
import com.t4.androidclient.model.helper.LiveStreamHelper;
import com.t4.androidclient.model.helper.UserHelper;
import com.t4.androidclient.model.livestream.LiveStream;
import com.t4.androidclient.model.livestream.User;
import com.t4.androidclient.ui.home.HomeFragment;
import com.t4.androidclient.ui.login.LoginFragment;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import viewModel.StreamViewModel;

public class AboutFragment extends Fragment {
    User userInfo = null ;
    TextView channelName;
    TextView channelSubNumber;
    Button subscribleButton;
    Button blockButton;
    TextView channelTypes;
    TextView description;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_channel_about, container, false);
        channelName = root.findViewById(R.id.channel_name);
        channelSubNumber = root.findViewById(R.id.channel_sub_number);
        channelTypes = root.findViewById(R.id.channel_type);
        description = root.findViewById(R.id.description);

        AboutFragment.About about = new AboutFragment.About(new AboutFragment.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    UserHelper userHelper = new UserHelper();
                    JSONObject userJSON = new JSONObject(output);
                    if (userJSON.getInt("statusCode") == 200) {
                        User userInfo = userHelper.parseUserJson(userJSON);
                        System.out.println("True Response");
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        String[] values = new String[2];
        values[0] = "userID";
        values[1] = "1" ; // Lay ID cua thang channel khi click vao , vi du 1
        about.execute(values);
        channelName.setText(userInfo.getNickname());
        channelSubNumber.setText(userInfo.getSubscribeTotal());
        for(int i=0;i<=)
        channelTypes.setText(userInfo.favouriteType.get(0));
        return root;
    }

    public interface AsyncResponse {
        void processFinish(String output);
    }
    // ============ DO GET INFO USER ==================================
    private class About extends AsyncTask<String, Integer, String> {
        public AboutFragment.AsyncResponse asyncResponse = null;

        public About(AboutFragment.AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... token) {
            String URL_GET_INFO_BY_ID = Host.API_HOST_IP + "/user/auth/info/"+token[1];
            Request request = HttpClient.buildGetRequest(URL_GET_INFO_BY_ID);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }
}
}