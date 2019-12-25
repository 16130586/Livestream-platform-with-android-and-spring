package com.t4.androidclient.ui.channel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.t4.androidclient.R;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Authentication;
import com.t4.androidclient.contraints.Host;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.httpclient.SqliteAuthenticationHelper;
import com.t4.androidclient.model.helper.StreamTypeHelper;
import com.t4.androidclient.model.helper.UserHelper;
import com.t4.androidclient.model.livestream.StreamType;
import com.t4.androidclient.model.livestream.User;
import com.t4.androidclient.ui.login.LoginRegisterActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;

public class AboutFragment extends Fragment {
    User getUser = null ;
    User currentUser = null ;
    TextView channelName;
    TextView channelSubNumber;
    Button subscribleButton;
    Button blockButton;
    TextView channelTypes;
    TextView description;
     ImageView channelBackground;

    CircleImageView channelImage;
    int ownerID;
    List<StreamType> typeList = new ArrayList<StreamType>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_channel_about, container, false);
        channelName = root.findViewById(R.id.channel_name);
        channelSubNumber = root.findViewById(R.id.channel_sub_number);
        channelTypes = root.findViewById(R.id.channel_type);
        description = root.findViewById(R.id.description);
        subscribleButton = root.findViewById(R.id.btn_subscrible);
        channelImage = root.findViewById(R.id.channel_image);
        channelBackground =  root.findViewById(R.id.background_channel);
        ownerID = getActivity().getIntent().getIntExtra("owner_id",-1);






        SqliteAuthenticationHelper db = new SqliteAuthenticationHelper(getContext());
        Authentication.TOKEN = db.getToken();
        //Check login status
        if (Authentication.TOKEN == null || Authentication.TOKEN.isEmpty()) {
            subscribleButton.setText("Subscribe");
            subscribleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Please login to subscrible channel !",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginRegisterActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            // Check status subscribe with channel owner , place before to call below
            CheckSubscribeTask checkSubscribeTask = new CheckSubscribeTask(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                    if (response != null && response.statusCode == 200) {
                        boolean result = (boolean) response.data;
                        if(result==true){ // True - Can subscrible
                            subscribleButton.setText("Subscribe");
                            subscribleButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    SubscribeTask subscribeTask = new SubscribeTask(new AsyncResponse() {
                                        @Override
                                        public void processFinish(String output) {
                                            ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                                            if (response != null && response.statusCode == 200) {
                                                boolean result = (boolean) response.data;
                                                if(result==true){
                                                    Toast.makeText(getActivity(), "Subscribe channel successfully !",
                                                            Toast.LENGTH_SHORT).show();
                                                    // Reload current fragment
                                                    getActivity().finish();
                                                    getActivity().overridePendingTransition(0, 0);
                                                    startActivity(getActivity().getIntent());
                                                    getActivity().overridePendingTransition(0, 0);
                                                }
                                            }
                                        }});
                                    String[] subData = new String[2];
                                    subData[0]=currentUser.getId()+"";
                                    subData[1]=ownerID+"";
                                    subscribeTask.execute(subData);
                                }
                            });
                        }else{ // False - Can't subscrible
                            subscribleButton.setText("Un Subscribe");
                            subscribleButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    UnSubscribeTask unSubscribeTask = new UnSubscribeTask(new AsyncResponse() {
                                        @Override
                                        public void processFinish(String output) {
                                            ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                                            if (response != null && response.statusCode == 200) {
                                                boolean result = (boolean) response.data;
                                                if(result==true){
                                                    Toast.makeText(getActivity(), "Un Subscribe channel successfully !",
                                                            Toast.LENGTH_SHORT).show();
                                                    // Reload current fragment
                                                    getActivity().finish();
                                                    getActivity().overridePendingTransition(0, 0);
                                                    startActivity(getActivity().getIntent());
                                                    getActivity().overridePendingTransition(0, 0);
                                                }
                                            }
                                        }});
                                    String[] subData = new String[2];
                                    subData[0]=currentUser.getId()+"";
                                    subData[1]=ownerID+"";
                                    unSubscribeTask.execute(subData);
                                }
                            });
                        }
                    }
                }});

            // If login already then check status subscrible
            // Get current user info to do task
            CurrentInfo currentInfo = new CurrentInfo(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    if (output != null && !output.isEmpty()) {
                        ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                        if (response != null && response.statusCode == 200) {
                            Map<String, Object> rawData = (Map<String, Object>) response.data;
                            currentUser = UserHelper.parseUserJson(rawData);

                        }
                    }
                    // Get currentUser successfully , do check Status Subscribe
                    String[] subData = new String[2];
                    subData[0]=currentUser.getId()+"";
                    subData[1]=ownerID+"";
                    checkSubscribeTask.execute(subData); //Call above method
                }

            });
            currentInfo.execute();
        }




        ChannelInfo channelInfo = new ChannelInfo(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    UserHelper userHelper = new UserHelper();
                    JSONObject data = new JSONObject(output);
                    if (data.getInt("statusCode") == 200) {
                        String userString = data.getString("data");
                        JSONObject userJSON = new JSONObject(userString);
                        getUser = userHelper.parseUserJson(userJSON);
                        // Render UImà
                        channelName.setText(getUser.getNickname());
                        String avatarURL = getUser.getAvatar();
                        if (avatarURL != null && !avatarURL.isEmpty())
                            Glide.with(channelImage.getContext()).load(avatarURL.startsWith("http") ? avatarURL : Host.API_HOST_IP + avatarURL) // plays as url
                                    .placeholder(R.drawable.ic_fire).centerCrop().into(channelImage);

                        String backgroundURL = getUser.getBackground();
                        if (backgroundURL != null && !backgroundURL.isEmpty())
                            Glide.with(channelBackground.getContext()).load(avatarURL.startsWith("http") ? backgroundURL : Host.API_HOST_IP + backgroundURL) // plays as url
                                    .placeholder(R.drawable.ic_fire).centerCrop().into(channelBackground);

                        channelSubNumber.setText(getUser.getSubscribeTotal()+" người theo dõi");
                        description.setText(getUser.getDescription());
                        AboutFragment.StreamTypes streamTypes = new AboutFragment.StreamTypes(new AsyncResponse() {
                            @Override
                            public void processFinish(String output) {
                                try {
                                    StreamTypeHelper streamTypeHelper = new StreamTypeHelper();
                                       typeList = streamTypeHelper.parseGenreJson(output);
                                        // Kiễm tra nếu null thì bỏ qua
                                        if(typeList!=null) {
                                            String types = "";
                                            for (int i = 0; i < typeList.size() - 1; i++) {
                                                types += typeList.get(i).getTypeName() + ", ";
                                            }
                                            types += typeList.get(typeList.size()-1).getTypeName() ;
                                            channelTypes.setText(types);
                                        }
                                    }
                               catch (Exception o) {
                                        o.printStackTrace();
                                    }
                            }});
                        String[] values = new String[2];
                        values[0] = "userID";
                        values[1] = String.valueOf(ownerID);
                        streamTypes.execute(values); // yeu cau task chay lay danh sach ListStream theo userID
                    }
            }catch (Exception e) {
                    e.printStackTrace();
                }
        }
        });
        String[] values = new String[2];
        values[0] = "userID";
        values[1] = String.valueOf(ownerID); // Lay ID cua thang channel khi click vao
        channelInfo.execute(values);
        return root;
    }
    // ============ DO GET INFO USER  ==================================
    private class ChannelInfo extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public ChannelInfo(AsyncResponse asyncResponse) {
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
            Request request = HttpClient.buildGetRequest(Api.URL_GET_STREAM_TYPES_BY_ID+"/"+token[1]);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }

    private class CurrentInfo extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public CurrentInfo(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... data) {
            Request request = HttpClient.buildGetRequest(Api.URL_GET_INFO, Authentication.TOKEN);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }

    private class CheckSubscribeTask extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;
        public CheckSubscribeTask(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }
        @Override
        protected String doInBackground(String... data) {
            Map<String,String> dataMap = new HashMap<>();
            dataMap.put("subscriberID",data[0]);
            dataMap.put("publisherID",data[1]);
            Request request = HttpClient.buildPostRequest(Api.URL_CHANNEL_CHECK_SUBSCRIBE,dataMap);
            return HttpClient.execute(request);
        }
        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }

    private class SubscribeTask extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;
        public SubscribeTask(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }
        @Override
        protected String doInBackground(String... data) {
            Map<String,String> dataMap = new HashMap<>();
            dataMap.put("subscriberID",data[0]);
            dataMap.put("publisherID",data[1]);
            Request request = HttpClient.buildPostRequest(Api.URL_CHANNEL_SUBSCRIBE,dataMap);
            return HttpClient.execute(request);
        }
        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }

    private class UnSubscribeTask extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;
        public UnSubscribeTask(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }
        @Override
        protected String doInBackground(String... data) {
            Map<String,String> dataMap = new HashMap<>();
            dataMap.put("subscriberID",data[0]);
            dataMap.put("publisherID",data[1]);
            Request request = HttpClient.buildPostRequest(Api.URL_CHANNEL_UNSUBSCRIBE,dataMap);
            return HttpClient.execute(request);
        }
        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }
}
