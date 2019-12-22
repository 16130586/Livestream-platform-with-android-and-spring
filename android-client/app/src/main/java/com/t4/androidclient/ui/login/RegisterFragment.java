package com.t4.androidclient.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.t4.androidclient.R;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;


public class RegisterFragment extends Fragment {
    private final int PICK_PHOTO_FOR_AVATAR = 1;
    private static final String REGISTER_KEY_AVATAR = "avatar";
    private static final String REGISTER_KEY_USERNAME = "userName";
    private static final String REGISTER_KEY_PASSWORD = "password";
    private static final String REGISTER_KEY_NICKNAME = "nickName";
    private static final String REGISTER_KEY_EMAIL = "gmail";
    ProgressBar loadingProgressBar;
    private Map<String, String> keyValues = new HashMap<>();
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_register, container, false);

        // values
        EditText username = root.findViewById(R.id.register_username);
        EditText password = root.findViewById(R.id.register_password);
        EditText email = root.findViewById(R.id.register_gmail);
        EditText nickname = root.findViewById(R.id.register_nickname);
        loadingProgressBar = root.findViewById(R.id.register_loading);

        //button gallery
        Button btnGallery = root.findViewById(R.id.register_choose_avatar);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        //button registry
        Button btnRegis = root.findViewById(R.id.btn_register);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                keyValues.put(REGISTER_KEY_USERNAME, username.getText().toString());
                keyValues.put(REGISTER_KEY_PASSWORD, password.getText().toString());
                keyValues.put(REGISTER_KEY_EMAIL, email.getText().toString());
                keyValues.put(REGISTER_KEY_NICKNAME, nickname.getText().toString());
                Register register = new Register(new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        ApiResponse apiResponse = JsonHelper.deserialize(output, ApiResponse.class);
                        System.out.println(apiResponse.data);
                        loadingProgressBar.setVisibility(View.GONE);
                        if (apiResponse.statusCode == 200)
                            Toast.makeText(getActivity(), "Registry Successful!", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(), apiResponse.data.toString(), Toast.LENGTH_LONG).show();

                    }
                });

                register.execute();
            }
        });

        return root;
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                //img
                ImageView imageView = (ImageView) root.findViewById(R.id.image_register_avatar);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);

                //base 64
                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                bytes = output.toByteArray();
                String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                keyValues.put(REGISTER_KEY_AVATAR, base64Image);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // AsyncTask to get the genre list
    private class Register extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public Register(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... input) {
            Request request = HttpClient.buildPostRequest(Api.URL_REGISTER, keyValues);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }
}