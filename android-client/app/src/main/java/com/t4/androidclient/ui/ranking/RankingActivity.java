package com.t4.androidclient.ui.ranking;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.t4.androidclient.MainScreenActivity;
import com.t4.androidclient.R;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Host;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.model.helper.UserHelper;
import com.t4.androidclient.model.livestream.User;

import java.util.List;

import okhttp3.Request;

public class RankingActivity extends Activity {
    ListView rankingItems;
    List<User> userList;
    RankingUserAdapter rankingUserAdapter;
    ProgressBar progressBar;
    TextView textBuyRanking;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        progressBar = findViewById(R.id.rank_loading);
        textBuyRanking = findViewById(R.id.buy_ranking);
        textBuyRanking.setText(Html.fromHtml("<a href=\"" + Host.API_HOST_IP + "/ranks/buy/" + MainScreenActivity.user.getId() + "\"> Buy Ranking NOW!</a>"));
        textBuyRanking.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        getTopRankingUser();

    }

    public void getTopRankingUser() {
        progressBar.setVisibility(View.VISIBLE);
        GetTopRanking getTopRanking = new GetTopRanking(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if (output == null) return;
                try {
                    ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                    if (response != null && response.statusCode == 200) {
                        userList = UserHelper.parseListUserJson(output);
                        rankingItems = findViewById(R.id.ranking_items);
                        rankingUserAdapter = new RankingUserAdapter(RankingActivity.this, userList);
                        rankingItems.setAdapter(rankingUserAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
        getTopRanking.execute();
    }

    // AsyncTask get top 10 ranking user
    private class GetTopRanking extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public GetTopRanking(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... urls) {
            Request request = HttpClient.buildGetRequest(Api.URL_GET_TOP_RANKING);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }
}
