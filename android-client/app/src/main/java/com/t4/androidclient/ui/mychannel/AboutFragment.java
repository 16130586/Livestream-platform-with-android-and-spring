package com.t4.androidclient.ui.mychannel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import okhttp3.Request;

public class AboutFragment extends Fragment {
    User getUser = null ;
    EditText channelName;
    TextView channelSubNumber;
    ImageButton changeAboutButton;
    ImageButton changeImageButton;
    TextView channelTypes;
    EditText description;
    int ownerID;
    List<StreamType> typeList = new ArrayList<StreamType>();
    boolean editStatus = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mychannel_about, container, false);
        channelName = root.findViewById(R.id.channel_name);
        channelSubNumber = root.findViewById(R.id.channel_sub_number);
        channelTypes = root.findViewById(R.id.channel_type);
        description = root.findViewById(R.id.description);
        changeAboutButton = root.findViewById(R.id.btn_change_about);
        changeImageButton = root.findViewById(R.id.btn_change_image);
        ownerID = getActivity().getIntent().getIntExtra("owner_id",-1);

        changeAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editStatus==false){
                    channelName.setEnabled(true);
                    description.setEnabled(true);
                    changeAboutButton.setImageResource(R.drawable.ic_done_black_24dp);
                    editStatus=true;
                }else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    alertDialogBuilder.setMessage("Are you sure to change the information ?");
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            AboutFragment.SaveChanges saveChangesTask = new AboutFragment.SaveChanges(new AsyncResponse() {
                                @Override
                                public void processFinish(String output) {
                                    ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                                    if (response != null && response.statusCode == 200) {
                                        boolean result = (boolean) response.data;
                                        if(result==true){
                                            channelName.setEnabled(false);
                                            description.setEnabled(false);
                                            editStatus = false;
                                            changeAboutButton.setImageResource(R.drawable.ic_settings_black_24dp);
                                            Toast.makeText(getActivity(),"Saving changes successfully",Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(getActivity(),"Saving changes failed",Toast.LENGTH_LONG).show();
                                        }
                                    }else{
                                        Toast.makeText(getActivity(),"Saving changes failed",Toast.LENGTH_LONG).show();
                                    }
                                }});
                            String[] subData = new String[3];
                            subData[0]=ownerID+"";
                            subData[1]=channelName.getText()+"";
                            subData[2]=description.getText()+"";
                            saveChangesTask.execute(subData);
                        }
                    });

                    alertDialogBuilder.setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(),"Continue to edit information",Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

            }
        });

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
        String[] values = new String[1];
        values[0] = String.valueOf(ownerID); // Lay ID cua thang channel khi click vao
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
            Request request = HttpClient.buildGetRequest(Api.URL_GET_INFO_BY_ID+"/"+token[0]);
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

    private class SaveChanges extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public SaveChanges(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... token) {
            Map<String, String> keyValues = new HashMap<>();
            keyValues.put("userID", token[0]);
            keyValues.put("newName", token[1]);
            keyValues.put("newDescription", token[2]);
            Request request = HttpClient.buildPostRequest(Api.URL_UPDATE_ABOUT,keyValues);
            return HttpClient.execute(request);        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }
}
