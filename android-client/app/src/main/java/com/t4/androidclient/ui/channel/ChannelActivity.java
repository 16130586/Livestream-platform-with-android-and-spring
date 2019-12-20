package com.t4.androidclient.ui.channel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.t4.androidclient.MainScreenActivity;
import com.t4.androidclient.R;
import com.t4.androidclient.searching.MakeSuggestion;
import com.t4.androidclient.searching.Suggestion;
import com.t4.androidclient.searching.asyn;

import java.util.ArrayList;
import java.util.List;

import viewModel.StreamViewModel;


public class ChannelActivity extends AppCompatActivity implements MakeSuggestion {
    FloatingSearchView mSearchView;
    ImageView app_logo;
    MakeSuggestion makeSuggestion = this;
    private asyn a = null;
    private int ownerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        bindNavigateData(getIntent());
        mSearchView  = findViewById(R.id.floating_search_view);
        mSearchView.setSearchHint("Channel "+getIntent().getStringExtra("DATA2"));

        /////////// thêm bottom menu navigation
        BottomNavigationView navView = findViewById(R.id.menu_channel_view);
        NavController navController = Navigation.findNavController(this, R.id.channel_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        // Click back về Home
        app_logo = findViewById(R.id.back_logo);
        app_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
                Intent backHome = new Intent(ChannelActivity.this, MainScreenActivity.class);
                startActivity(backHome);
                overridePendingTransition(0, 0);
            }
        });


        /////////////  thêm search vào
        mSearchView = findViewById(R.id.floating_search_view);
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {

            }
        });

        // cái này là kiểm tra thay đổi trên search
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {
                    mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
                        @Override
                        public void onSearchTextChanged(String oldQuery, String newQuery) {
                            if (!oldQuery.equals("") && newQuery.equals("")) {
                                mSearchView.clearSuggestions();
                            } else {
                                mSearchView.showProgress();
                                if (a != null) {
                                    a.cancel(true);
                                }
                                a = (asyn) new asyn(makeSuggestion).execute("http://suggestqueries.google.com/complete/search?output=firefox&hl=vi&q=" + newQuery);

                            }
                        }
                    });

                }
            }
        });

    }

    private void bindNavigateData(Intent previousNavigationData) {
        this.ownerId = previousNavigationData.getIntExtra("DATA", -1);
    }


    @Override
    public void getSuggestion(List<Suggestion> suggestions) {
        mSearchView.swapSuggestions(suggestions);
        mSearchView.hideProgress();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // kiểm tra kết quả search
        if (requestCode == 0 && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            mSearchView.setFocusable(true);
            mSearchView.setSearchText(results.get(0));
        }
    }
}
