package com.t4.androidclient.external;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.t4.androidclient.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
public class FacebookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
    }
}
