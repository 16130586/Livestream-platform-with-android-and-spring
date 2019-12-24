package com.t4.androidclient.ui.mychannel;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.t4.androidclient.MainScreenActivity;
import com.t4.androidclient.R;
import com.t4.androidclient.searching.MakeSuggestion;
import com.t4.androidclient.searching.Suggestion;
import com.t4.androidclient.searching.asyn;
import com.t4.androidclient.ui.channel.ChannelActivity;

import java.util.ArrayList;
import java.util.List;


public class MyChannelActivity extends AppCompatActivity implements MakeSuggestion {
    FloatingSearchView mSearchView;
    ImageView app_logo;
    MakeSuggestion makeSuggestion = this;
    private asyn a = null;
    int ownerId ;
    String channelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychannel);
        mSearchView  = findViewById(R.id.mychannel_floating_search_view);

        /////////// thêm bottom menu navigation
        String fragment_des = getIntent().getStringExtra("fragment_des");
        System.out.println(fragment_des);

        BottomNavigationView navView = findViewById(R.id.menu_mychannel_view);
        NavController navController = Navigation.findNavController(this, R.id.mychannel_host_fragment);
        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.mychannel_navigation);
        NavigationUI.setupWithNavController(navView, navController);


        if(fragment_des!=null){
           if(fragment_des.equals("your_streams")){

        }
           else if(fragment_des=="your_watched_streams"){
               navGraph.setStartDestination(R.id.fragment_mychannel_watched_streams);
           }
           else if(fragment_des=="your_subscribed_channels"){
               navGraph.setStartDestination(R.id.fragment_mychannel_subscribed_channels);
           }
           else if(fragment_des=="your_channel"){
               navGraph.setStartDestination(R.id.fragment_mychannel_about);
           }
           navController.setGraph(navGraph);
       }

        bindNavigateData(getIntent());
        mSearchView.setSearchHint("Your channel : "+ channelName);

        // Click back về Home
//        app_logo = findViewById(R.id.mychannel_back_logo);
//        app_logo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                overridePendingTransition(0, 0);
//                Intent backHome = new Intent(MyChannelActivity.this, MainScreenActivity.class);
//                startActivity(backHome);
//                overridePendingTransition(0, 0);
//            }
//        });


        /////////////  thêm search vào
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
    private void bindNavigateData(Intent previousNavigationData) {
        this.ownerId = previousNavigationData.getIntExtra("owner_id", -1);
        this.channelName=previousNavigationData.getStringExtra("channel_name");
    }
}
