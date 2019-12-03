package com.t4.androidclient.ui.login;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.t4.androidclient.R;

public class LoginRegisterActivity extends AppCompatActivity {

    public static CallbackManager mCallbackManager;
    public static boolean isLoggedIn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        /////////// thêm  menu navigation
        BottomNavigationView navView = findViewById(R.id.login_register_view);
        NavController navController = Navigation.findNavController(this, R.id.login_register_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        mCallbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
