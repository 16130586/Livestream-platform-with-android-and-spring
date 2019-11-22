package com.t4.androidclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.t4.androidclient.R;
import com.t4.androidclient.adapter.CreateLiveGenreAdapter;
import com.t4.androidclient.model.livestream.FacebookUser;
import com.t4.androidclient.model.livestream.LiveStream;
import com.t4.androidclient.model.livestream.TokenPermission;
import com.t4.androidclient.model.livestream.TokenPermissionHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreateLiveActivity extends Activity {
    private List<String> genreList, genreListByUser, platformListByUser;
    private EditText textTitle,  textUrl;
    private TextView tokenPermissionChecker;

    private Button btnSave;
    private ImageButton btnFacebook;
    private LoginButton btnLoginFacebook;

    private Adapter genreAdapter;
    private ListView lvGenre;
    private CallbackManager callbackManager;

    private FacebookUser facebookUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_live);
        setUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setUp() {
        genreList = getGenreListFromServer();
        genreListByUser = new ArrayList<>();
        platformListByUser = new ArrayList<>();

        //text
        textTitle = findViewById(R.id.text_create_live_title);
        textUrl = findViewById(R.id.text_create_live_url);
        tokenPermissionChecker = findViewById(R.id.create_live_token_checker);

        //list view for genre
        genreAdapter = new CreateLiveGenreAdapter(this, android.R.layout.activity_list_item, genreList);
        lvGenre = (ListView) findViewById(R.id.lv_create_live_genre);
        lvGenre.setAdapter((ListAdapter) genreAdapter);

        //button save
        btnSave = findViewById(R.id.btn_create_live_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveStream liveStream = saveLiveStream();
                System.out.println(liveStream.toString());
                sendLiveStreamToServer();
            }
        });

        // button facebook
        callbackManager = CallbackManager.Factory.create();
        btnFacebook = findViewById(R.id.btn_create_live_facebook);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLoginFacebook.performClick();
            }
        });

        btnLoginFacebook = (LoginButton) findViewById(R.id.btn_login_facebook_1);
        btnLoginFacebook.setPermissions("user_location", "publish_video" , "user_events" , "manage_pages");
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebookUser = new FacebookUser();
                facebookUser.setUserId(loginResult.getAccessToken().getUserId());
                facebookUser.setAccessToken(loginResult.getAccessToken().getToken());
                checkAccesToken(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                System.out.println("cancel");
            }

            @Override
            public void onError(FacebookException e) {
                System.out.println("failed");
            }
        });

        //test
        checkAccesToken(":asd");
    }

    private List<String> getGenreListFromServer() {
        // server
        ArrayList<String> genreList = new ArrayList<>();
        genreList.add("Fun");
        genreList.add("Game");
        return genreList;
    }

    public void onCheckboxClicked(View view) {
        CheckBox checkBox = (CheckBox) view;
        boolean checked = checkBox.isChecked();

        if (checked) {
            genreListByUser.add(checkBox.getText().toString());
        } else {
            genreListByUser.remove(checkBox.getText().toString());
        }
        System.out.println(genreListByUser.toString());
    }

    public LiveStream saveLiveStream() {
        LiveStream liveStream = new LiveStream();
        liveStream.setTitle(textTitle.getText().toString());
        liveStream.setThumbnailUrl(textUrl.getText().toString());
        liveStream.setGenreList(this.genreListByUser);

        if (tokenPermissionChecker.getText() == null ) {

        } else if (tokenPermissionChecker.getText().toString().equals("true")) {
            liveStream.setFacebookUser(this.facebookUser);
        } else {
            System.out.println("facebook permission is wrong");
            return null;
        }

        return liveStream;
    }

    public void sendLiveStreamToServer() {

    }

    public void checkAccesToken(String accessToken) {
        CheckTokenPermission checkTokenPermission = new CheckTokenPermission(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                tokenPermissionChecker.setText(output);
            }
        });
        String tempToken = "EAAKClF4nNmIBAJnQiZBWJaLNocaeloDqR8OrMAFpdWTPc5RoRqtUbboVEpG6Jv6EAolmdX2grGjixkZAwCl0GupyqZB6vrPmzX6ah3ADHbHl4Lp5r5lvu7xRVIQCQ84kgINk5uOriTjHTmK4DVRhGrmHkMJkjBbDVm3a7uf6jenc3zHfh3lsxFkoCdtEzWjMFZByExgahwZDZD";
        checkTokenPermission.execute(tempToken);
    }

    // interface AsyncResponse
    public interface AsyncResponse {
        void processFinish(String output);
    }

    // AsyncTask to check token permission
    private class CheckTokenPermission extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        // constructor
        public CheckTokenPermission(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... accessTokens) {
            String url = "https://graph.facebook.com/me/permissions?access_token=" + accessTokens[0];
            System.out.println("THe url: " + url);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            TokenPermissionHelper helper = new TokenPermissionHelper();

            try (Response response = client.newCall(request).execute()) {
                String rs = response.body().string();
                List<TokenPermission> tokenPermissionList = helper.parseTokenPermissionJson(rs);
                boolean checker = helper.checkTokenPermissions(tokenPermissionList);

                return String.valueOf(checker);
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
