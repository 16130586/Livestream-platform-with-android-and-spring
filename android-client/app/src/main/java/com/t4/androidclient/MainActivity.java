package com.t4.androidclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//}
public class MainActivity extends Activity {

    private TextView mTvInfo;
    private LoginButton mBtnLoginFacebook;
    private CallbackManager mCallbackManager;
    private Activity current = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        mCallbackManager = CallbackManager.Factory.create();
        mTvInfo = (TextView) findViewById(R.id.tv_info);
        mBtnLoginFacebook = (LoginButton) findViewById(R.id.btn_login_facebook1);
        mBtnLoginFacebook.setPermissions("user_location", "publish_video" , "user_events" , "manage_pages");
        mBtnLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mTvInfo.setText("User ID: " + loginResult.getAccessToken().getUserId() + "\n" +
                        "Auth Token: " + loginResult.getAccessToken().getToken());
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                Toast.makeText(getApplicationContext(), "is loggin" + isLoggedIn, Toast.LENGTH_LONG);
                LoginManager.getInstance().logInWithReadPermissions(current , Arrays.asList("user_events" , "manage_pages"));

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, accessToken.getToken());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

            }

            @Override
            public void onCancel() {
                mTvInfo.setText("Login canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                mTvInfo.setText("Login failed.");
            }
        });
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        //
        if (accessToken != null && !accessToken.isExpired()) {
            boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, accessToken.getToken() + "------" + accessToken.getUserId() );
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        }
//        if (accessToken != null  && !accessToken.isExpired()) {
//            GraphRequest request = null;
//            try {
//                request = GraphRequest.newPostRequest(
//                        accessToken,
//                        "/" + accessToken.getUserId() + "/live_videos",
//                        new JSONObject("{\"title\":\"Today's Live Video\",\"description\":\"This is the live video for today.\"}"),
//                        new GraphRequest.Callback() {
//                            @Override
//                            public void onCompleted(GraphResponse response) {
//                                if(response != null){
//                                // Insert your code here
//                                Toast.makeText(getApplicationContext(), response.getJSONObject().toString(), Toast.LENGTH_LONG);
//                                Intent sendIntent = new Intent();
//                                sendIntent.setAction(Intent.ACTION_SEND);
//                                sendIntent.putExtra(Intent.EXTRA_TEXT, response.getJSONObject().toString());
//                                sendIntent.setType("text/plain");
//
//                                Intent shareIntent = Intent.createChooser(sendIntent, null);
//                                startActivity(shareIntent);
//                                }else {
//                                    Toast.makeText(getApplicationContext(), "some thing wrong", Toast.LENGTH_LONG);
//                                }
//                            }
//                        });
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            request.executeAsync();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}