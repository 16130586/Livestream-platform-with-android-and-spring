package com.t4.androidclient.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.t4.androidclient.MainScreenActivity;
import com.t4.androidclient.R;
import com.facebook.login.LoginResult;
import com.t4.androidclient.ScreenshotActivity;

import java.util.Arrays;

public class SocialLoginFragment extends Fragment {

    private LoginButton mBtnFacebook;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Vì chưa đồng bộ tài khoản Facebook và Local nên đăng nhập tạm thời gọi đến Login của Facebook");
        View root = inflater.inflate(R.layout.fragment_social_login, container, false);
        mBtnFacebook = (LoginButton) root.findViewById(R.id.btn_login_facebook);
        mBtnFacebook.setPermissions("user_location", "publish_video", "user_events", "manage_pages");
        mBtnFacebook.registerCallback(LoginRegisterActivity.mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("user_events"));
                Intent mainScreen = new Intent(getActivity(),MainScreenActivity.class);
                // bỏ code của Tường vì nó hiện bảng coppy Token mỗi khi tab qua SocialLoginFragment này
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, accessToken.getToken());
//                sendIntent.setType("text/plain");
//                Intent shareIntent = Intent.createChooser(sendIntent, "Chọn gì nào");
                startActivity(mainScreen);
                System.out.println("Đăng nhập thành công");
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        if (accessToken != null && !accessToken.isExpired()) {
//            LoginRegisterActivity.isLoggedIn = accessToken != null && !accessToken.isExpired();
//            Intent sendIntent = new Intent();
//            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT, accessToken.getToken() + "------" + accessToken.getUserId());
//            sendIntent.setType("text/plain");
//            Intent shareIntent = Intent.createChooser(sendIntent, null);
//            startActivity(shareIntent);
//        }
        return root;
    }

}


