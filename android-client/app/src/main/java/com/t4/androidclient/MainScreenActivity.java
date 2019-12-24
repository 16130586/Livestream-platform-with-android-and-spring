package com.t4.androidclient;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.t4.androidclient.activity.CreateLiveActivity;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Authentication;
import com.t4.androidclient.contraints.Host;
import com.t4.androidclient.core.ApiResponse;
import com.t4.androidclient.core.AsyncResponse;
import com.t4.androidclient.core.JsonHelper;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.httpclient.SqliteAuthenticationHelper;
import com.t4.androidclient.model.livestream.User;
import com.t4.androidclient.model.helper.UserHelper;
import com.t4.androidclient.searching.MakeSuggestion;
import com.t4.androidclient.searching.Suggestion;
import com.t4.androidclient.searching.asyn;
import com.t4.androidclient.ui.login.LoginRegisterActivity;
import com.t4.androidclient.ui.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;


public class MainScreenActivity extends AppCompatActivity implements MakeSuggestion {
    FloatingSearchView mSearchView;
    DrawerLayout mDrawerLayout;
    ImageView app_logo;
    MakeSuggestion makeSuggestion = this;
    NavigationView slide_view;
    User user;
    public ProgressBar progressBar;
    private asyn a = null;
    private LoginButton mBtnFacebook;
    private CallbackManager mCallbackManager;
    private Activity current = this;
    private Button btn_login;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mCallbackManager = CallbackManager.Factory.create();
        progressBar = findViewById(R.id.main_loading);
        ///////////// thêm slide menu navigation
        mDrawerLayout = findViewById(R.id.drawer_layout);
        slide_view = findViewById(R.id.slide_view);

        // get token from sqlite & add to contraints
        setToken();
        //==============================================================================
        // Validate token & get user info if token valid.
        //================================================================================
        progressBar.setVisibility(View.VISIBLE);
        if (Authentication.TOKEN == null || Authentication.TOKEN.isEmpty()) {
            // TODO handle in case null token
            System.out.println("=========================================================================================");
            System.out.println("null token");
            doProcessNotLogin();
            System.out.println("=========================================================================================");
        } else {
            InfoUser infoUser = new InfoUser(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    if (output != null && !output.isEmpty()) {
                        ApiResponse response = JsonHelper.deserialize(output, ApiResponse.class);
                        if (response != null && response.statusCode == 200) {
                            Map<String, Object> rawData = (Map<String, Object>) response.data;
                            user = UserHelper.parseUserJson(rawData);
                            doProcessLoggedin();
                            return;
                        }
                    }
                    doProcessNotLogin();
                    progressBar.setVisibility(View.GONE);
                }
            });
            infoUser.execute();
        }
        /**
         =======================================================================================================================================
         END LOGIN
         =======================================================================================================================================
         */

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        slide_view.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        progressBar.setVisibility(View.VISIBLE);
                        mDrawerLayout.closeDrawers();
                        int id = menuItem.getItemId();
                        if (id == R.id.your_videos) {
                            System.out.println("Chọn 1");
                        }
                        progressBar.setVisibility(View.GONE);
                        return true;
                    }

                });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        System.out.println("Mở menu");
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        System.out.println("Đóng menu");
                    }


                    @Override
                    public void onDrawerStateChanged(int newState) {
//                            AccessToken accessToken = AccessToken.getCurrentAccessToken();
//                            boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//                            if (isLoggedIn == true) {
//                                slide_view.getMenu().clear();
//                                slide_view.removeHeaderView(slide_view.getHeaderView(0));
//                                slide_view.inflateHeaderView(R.layout.slide_header);
//                                slide_view.inflateMenu(R.menu.menu_slide);
//                                btn_logout = slide_view.getHeaderView(0).findViewById(R.id.btn_logout);
//                                btn_logout.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        System.out.println("Vì chưa đồng bộ tài khoản Facebook và Local nên đăng xuất tạm thời gọi đến hàm Logout của Facebook");
//                                        Intent intent = new Intent(MainScreenActivity.this, LoginRegisterActivity.class);
//                                        startActivity(intent);
//                                    }
//                                });
//
//                            } else {
//                                slide_view.getMenu().clear();
//                                slide_view.removeHeaderView(slide_view.getHeaderView(0));
//                                slide_view.inflateHeaderView(R.layout.slide_header_not_login);
//                                btn_login = slide_view.getHeaderView(0).findViewById(R.id.btn_login);
//                                btn_login.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        Intent intent = new Intent(MainScreenActivity.this, LoginRegisterActivity.class);
//                                        startActivity(intent);
//                                    }
//                                });
//                           }
                    }


                });

        /////////// thêm bottom menu navigation
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (Authentication.ISLOGIN)
                    return NavigationUI.onNavDestinationSelected(menuItem, navController);
                else if (menuItem.getItemId() == R.id.navigation_inbox || menuItem.getItemId() == R.id.navigation_subscription){
                    mDrawerLayout = findViewById(R.id.drawer_layout);
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    return false;
                }
                return NavigationUI.onNavDestinationSelected(menuItem, navController);
            }
        });

        // Click logo về Home
        app_logo = findViewById(R.id.app_logo);
        app_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });


        /////////////  thêm search vào
        mSearchView = findViewById(R.id.floating_search_view);
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {

//                if (item.getItemId() == R.id.action_voice_rec) {
//                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                    startActivityForResult(intent, 0);
//                }
                /// mở slide menu bên thanh search
                //else
                 if (item.getItemId() == R.id.open_menu_slide) {
                    mDrawerLayout = findViewById(R.id.drawer_layout);
                    mDrawerLayout.openDrawer(GravityCompat.START);
                 } else if (item.getItemId() == R.id.search_advance) {
                     Intent intentSearch = new Intent(MainScreenActivity.this, SearchActivity.class);
                     startActivity(intentSearch);
                 } else if (item.getItemId() == R.id.create_live_stream) {
                    if (Authentication.ISLOGIN) {
                        Intent createLive = new Intent(MainScreenActivity.this, CreateLiveActivity.class);
                        startActivity(createLive);
                    } else {
                        mDrawerLayout = findViewById(R.id.drawer_layout);
                        mDrawerLayout.openDrawer(GravityCompat.START);
                    }
                }
            }
        });

        // on search action
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
            }

            @Override
            public void onSearchAction(String currentQuery) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(MainScreenActivity.this, SearchActivity.class);
                intent.putExtra("keywords", currentQuery);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void setToken() {
        SqliteAuthenticationHelper db = new SqliteAuthenticationHelper(this);
//        db.saveToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2QiLCJpYXQiOjE1NzU4NjE0OTQsImV4cCI6MTU3NTk0Nzg5NH0.OHBcqgcQX3EwNZUR0cbHmtTLljE_3wIB5E3fdjoVS14");
        Authentication.TOKEN = db.getToken();
    }

    @Override
    public void getSuggestion(List<Suggestion> suggestions) {
        mSearchView.swapSuggestions(suggestions);
        mSearchView.hideProgress();
    }

    // kiểm tra lỗi speech quốc gia
    public void checkVoiceRecognition() {
        // Check if voice recognition is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            Toast.makeText(this, "Voice recognizer not present",
                    Toast.LENGTH_SHORT).show();
        }
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

// =======================================================================
// ============ DO GET INFO USER ==================================
// AsyncTask to get the genre list
    private class InfoUser extends AsyncTask<String, Integer, String> {
        public AsyncResponse asyncResponse = null;

        public InfoUser(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected String doInBackground(String... data) {
            Request request = HttpClient.buildGetRequest(Api.URL_GET_INFO, Authentication.TOKEN);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
            asyncResponse.processFinish(result);
        }
    }

    public void doProcessNotLogin() {
        Authentication.ISLOGIN = false;
        slide_view.getMenu().clear();
        slide_view.inflateHeaderView(R.layout.slide_header_not_login);

        //  Thêm button login vào slide khi chua dang nhap
        btn_login = slide_view.getHeaderView(0).findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, LoginRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void doProcessLoggedin() {
        Authentication.ISLOGIN = true;
        slide_view.getMenu().clear();
        slide_view.inflateHeaderView(R.layout.slide_header);
        CircleImageView profileImage = slide_view.getHeaderView(0).findViewById(R.id.profile_image);
        TextView buySubscription = slide_view.getHeaderView(0).findViewById(R.id.buySubscription);
//        if (user.getSubscription() != null) {
//            TextView iconVIP = slide_view.findViewById(R.id.icon_vip);
//            iconVIP.setVisibility(View.VISIBLE);
//            buySubscription.setText("Your subscription will expires on " + user.subscription.getEndTime());
//            buySubscription.setTextSize(14);
//        } else {
            buySubscription.setText(Html.fromHtml("<a href=\"" + Host.API_HOST_IP + "/user/subscription/" + user.getId() + "\"> Upgrade To Premium Account</a>"));
            buySubscription.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
//        }

        if (user.avatar != null && !user.avatar.isEmpty())
            Glide.with(this).load(user.avatar.startsWith("http") ? user.avatar : Host.API_HOST_IP + user.avatar) // plays as url
                    .placeholder(R.drawable.ic_fire).centerCrop().into(profileImage);

        //  Thêm button logout vào slide khi da dang nhap
        btn_logout = slide_view.getHeaderView(0).findViewById(R.id.btn_logout);
        TextView profile_fullname = slide_view.getHeaderView(0).findViewById(R.id.profile_fullname);
        profile_fullname.setText("Hi " + user.getNickname() + ",");
//        TextView profile_email = slide_view.getHeaderView(0).findViewById(R.id.profile_email);
//        profile_email.setText("Your email: " + user.getGmail());
        if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
            // TODO show avatar
        }
    }

    public void logout() {
        new SqliteAuthenticationHelper(MainScreenActivity.this).deleteToken();
        MainScreenActivity.this.user = null;
        doProcessNotLogin();
    }
}

///////////// Thêm slide menu navigation



