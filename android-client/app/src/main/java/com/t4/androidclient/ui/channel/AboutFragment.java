package com.t4.androidclient.ui.channel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.t4.androidclient.R;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.model.helper.StreamTypeHelper;
import com.t4.androidclient.model.helper.UserHelper;
import com.t4.androidclient.model.livestream.StreamType;
import com.t4.androidclient.model.livestream.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class AboutFragment extends Fragment {
    User userInfo = null ; // moi dau t set null, xong parse thi t gán nó thánh thằng mới, rồi xuống render
    TextView channelName;
    TextView channelSubNumber;
    Button subscribleButton;
    Button blockButton;
    TextView channelTypes;
    TextView description;
    int ownerID;
    List<StreamType> typeList = new ArrayList<StreamType>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_channel_about, container, false);
        channelName = root.findViewById(R.id.channel_name);
        channelSubNumber = root.findViewById(R.id.channel_sub_number);
        channelTypes = root.findViewById(R.id.channel_type);
        description = root.findViewById(R.id.description);
        ownerID = getActivity().getIntent().getIntExtra("DATA",-1);

        AboutFragment.About about = new AboutFragment.About(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    UserHelper userHelper = new UserHelper();
                    JSONObject data = new JSONObject(output);
                    if (data.getInt("statusCode") == 200) {
                        String userString = data.getString("data");
                        JSONObject userJSON = new JSONObject(userString);
                        userInfo = userHelper.parseUserJson(userJSON);
                        System.out.println("True Response");
                        
                        // Render UImà
                        channelName.setText(userInfo.getNickname());
                        channelSubNumber.setText(userInfo.getSubscribeTotal()+" người theo dõi");
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
                                            types += typeList.get(typeList.size()-1).getTypeName() + " .";
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
        about.execute(values);


        return root;
    }
    // ============ DO GET INFO USER  ==================================
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
