package com.t4.androidclient.searching;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class asyn extends AsyncTask<String, Void, String> {

    //Đây là interface khi suggestion được xây dựng xong nó sẽ gọi tới hàm trong MainActivity.java
    MakeSuggestion makeSuggestion ;

    public asyn(MakeSuggestion makeSuggestion) {
        this.makeSuggestion = makeSuggestion;
    }

    private List<Suggestion> suggestions = new ArrayList<>();

    // Xây dựng okhttp
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {

            //arr_main chính là từ khoá bạn muốn được suggest
            // arr_sub chính là kết quả suggest được trả về từ google
            JSONArray arr_main = new JSONArray(s);
            JSONArray arr_sub = new JSONArray(arr_main.getString(1));
            for (int i = 0; i < arr_sub.length(); i++) {
                suggestions.add(new Suggestion(arr_sub.getString(i))) ;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //gởi dữ liệu sang MainActivity.java
        makeSuggestion.getSuggestion(suggestions);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            //Thời gian chờ xem thừ người dùng có nhập thêm kí tự nữa hay không.
            Thread.sleep(250);

            Request request = new Request.Builder()
                    .url(strings[0])
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}