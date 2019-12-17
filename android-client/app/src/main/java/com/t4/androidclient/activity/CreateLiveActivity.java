package com.t4.androidclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.t4.androidclient.R;
import com.t4.androidclient.adapter.CreateLiveGenreAdapter;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Authentication;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.model.helper.GenreHelper;
import com.t4.androidclient.model.livestream.FacebookUser;
import com.t4.androidclient.model.livestream.LiveStream;
import com.t4.androidclient.model.livestream.TokenPermission;
import com.t4.androidclient.model.helper.TokenPermissionHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreateLiveActivity extends Activity {
    private List<String> genreList, genreListByUser, platformListByUser;
    private EditText textTitle;
    private TextView tokenPermissionChecker;

    private Button btnSave, btnGallery;
    private ImageButton btnFacebook;
    private LoginButton btnLoginFacebook;

    private Adapter genreAdapter;
    private GridView gridView;
    private CallbackManager callbackManager;

    private FacebookUser facebookUser;
    private LiveStream liveStream;

    private final int PICK_PHOTO_FOR_AVATAR = 1;
    private final int FACEBOOK_OPTION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_live);
        setUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            byte[] bytes;
            byte[] buffer = new byte[8192];
            int bytesRead;

            try {
                Uri uri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                //img
                ImageView imageView = (ImageView) findViewById(R.id.test_gallery);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);

                //base 64
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                bytes = output.toByteArray();
                String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                liveStream.setThumbnail(base64Image);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //facebook callback
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == FACEBOOK_OPTION && resultCode == Activity.RESULT_OK) {
            facebookUser.setFacebookOption(data.getStringExtra("OPTION"));
            facebookUser.setFacebookOptionId(data.getStringExtra("ID"));
        }
    }

    private void setUp() {
        getGenreListFromServer();

        genreListByUser = new ArrayList<>();
        platformListByUser = new ArrayList<>();
        liveStream = new LiveStream();

        //text
        textTitle = findViewById(R.id.text_create_live_title);
        tokenPermissionChecker = findViewById(R.id.create_live_token_checker);

        //button save
        btnSave = findViewById(R.id.btn_create_live_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLiveStream();
                System.out.println(liveStream.toString());
                sendLiveStreamToServer();
            }
        });

        //button gallery
        btnGallery = findViewById(R.id.btn_create_live_gallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
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

        // button default from facebook
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

        //test - remove later
        //checkAccesToken(":asd");
    }

    private void getGenreListFromServer() {
        // server
        GetGenreList getGenreList = new GetGenreList(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                GenreHelper helper = new GenreHelper();
                List<String> genreListServer = helper.parseGenreJson(output);
                CreateLiveActivity.this.genreList = genreListServer;
                gridView = (GridView)findViewById(R.id.grid_create_live_genre);
                genreAdapter = new CreateLiveGenreAdapter(CreateLiveActivity.this, genreList);
                gridView.setAdapter((ListAdapter) genreAdapter);
            }
        });
        getGenreList.execute();

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

    public void saveLiveStream() {
        liveStream.setName(textTitle.getText().toString());
        liveStream.setGenreList(this.genreListByUser);

        if (tokenPermissionChecker.getText() == null ) {

        } else if (tokenPermissionChecker.getText().toString().equals("true")) {
            liveStream.setFacebookUser(this.facebookUser);
        } else {
            System.out.println("facebook permission is wrong");
        }
    }

    public void sendLiveStreamToServer() {
        CreateLiveStream createLiveStream = new CreateLiveStream(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                // TODO handle output stream & display
                System.out.println(output);
            }
        });
        createLiveStream.execute();
    }

    public void checkAccesToken(String accessToken) {
        CheckTokenPermission checkTokenPermission = new CheckTokenPermission(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                tokenPermissionChecker.setText(output);

                // permission true
                if (output.equals("true")) {
                    callFacebookOptionActivity();
                }
            }
        });

        // temp
        //String tempToken = "EAAKClF4nNmIBAJnQiZBWJaLNocaeloDqR8OrMAFpdWTPc5RoRqtUbboVEpG6Jv6EAolmdX2grGjixkZAwCl0GupyqZB6vrPmzX6ah3ADHbHl4Lp5r5lvu7xRVIQCQ84kgINk5uOriTjHTmK4DVRhGrmHkMJkjBbDVm3a7uf6jenc3zHfh3lsxFkoCdtEzWjMFZByExgahwZDZD";
        System.out.println(accessToken);
        checkTokenPermission.execute(accessToken);
    }


    // AsyncTask to get the genre list
    private class GetGenreList extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public GetGenreList(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... urls) {
            Request request = HttpClient.buildGetRequest(Api.URL_GET_ALL_GENRE);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
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

    // send live stream to server
    private class CreateLiveStream extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;


        // constructor
        public CreateLiveStream(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... data) {
            Request request = HttpClient.buildPostRequest(Api.URL_CREATE_LIVES_TREAM, liveStream, Authentication.TOKEN);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }

    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    public void callFacebookOptionActivity() {
        Intent intent = new Intent(this, FacebookOptionActivity.class);
        intent.putExtra("ACCESS_TOKEN", facebookUser.getAccessToken());
        startActivityForResult(intent, FACEBOOK_OPTION);
    }

}
