package com.t4.androidclient;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.t4.androidclient.activity.CreateLiveActivity;
import com.t4.androidclient.contraints.Api;
import com.t4.androidclient.contraints.Authentication;
import com.t4.androidclient.httpclient.HttpClient;
import com.t4.androidclient.httpclient.SqliteAuthenticationHelper;
import com.t4.androidclient.model.livestream.User;
import com.t4.androidclient.model.livestream.UserHelper;
import com.t4.androidclient.searching.MakeSuggestion;
import com.t4.androidclient.searching.Suggestion;
import com.t4.androidclient.searching.asyn;
import com.t4.androidclient.ui.login.LoginRegisterActivity;

import java.util.ArrayList;
import java.util.List;
import okhttp3.Request;


public class MainScreenActivity extends AppCompatActivity implements MakeSuggestion {
    FloatingSearchView mSearchView;
    DrawerLayout mDrawerLayout;
    ImageView app_logo;
    MakeSuggestion makeSuggestion = this;
    NavigationView slide_view;
    User user;
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
        ///////////// thêm slide menu navigation
        mDrawerLayout = findViewById(R.id.drawer_layout);
        slide_view = findViewById(R.id.slide_view);

        /**
         =======================================================================================================================================
         LOGIN FACEBOOK
         =======================================================================================================================================
         // Kiểm tra session đã đăng nhập thì slide menu khác
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        // Vì chưa đổng bộ Account nên tạm thời trạng thái đăng nhập là getToken của Facebook , khi có sẽ thay đổi
        if (isLoggedIn == true) {
            slide_view.getMenu().clear();
            slide_view.inflateHeaderView(R.layout.slide_header);
            slide_view.inflateMenu(R.menu.menu_slide);
            System.out.println("Đã đăng nhập");
            //  Thêm button logout vào slide khi da dang nhap
            btn_logout = slide_view.getHeaderView(0).findViewById(R.id.btn_logout);
            btn_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Vì chưa đồng bộ tài khoản Facebook và Local nên đăng xuất tạm thời gọi đến hàm Logout của Facebook");
                    LoginManager.getInstance().logOut();
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });
            TextView profile_fullname = slide_view.getHeaderView(0).findViewById(R.id.profile_fullname);
            profile_fullname.setText("ID đã đăng nhập : " + accessToken.getUserId());
        } else {
            slide_view.getMenu().clear();
            slide_view.inflateHeaderView(R.layout.slide_header_not_login);
            System.out.println("Chưa đăng nhập");

            //  Thêm button login vào slide khi chua dang nhap
            btn_login = slide_view.getHeaderView(0).findViewById(R.id.btn_login);
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Gọi đến SocialLoginFragment để login nhiều method khác nhau");
                    Intent intent = new Intent(MainScreenActivity.this, LoginRegisterActivity.class);
                    startActivity(intent);
                }
            });
        }
         =======================================================================================================================================
         END LOGIN FACEBOOK
         =======================================================================================================================================
         */

        /**
         =======================================================================================================================================
         LOGIN
         =======================================================================================================================================
         */
        // get token from sqlite & add to contraints
        setToken();
        //==============================================================================
        // Validate token & get user info if token valid.
        //================================================================================
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
                    user = UserHelper.parseUserJson(output);
                    doProcessLoggedin();
                }
            });
            infoUser.execute(Authentication.TOKEN);
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
                        mDrawerLayout.closeDrawers();
                        int id = menuItem.getItemId();
                        if (id == R.id.your_videos) {
                            System.out.println("Chọn 1");
                        }
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
                }
        );


        /////////// thêm bottom menu navigation
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);


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
                if (item.getItemId() == R.id.action_voice_rec) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent, 0);
                }
                /// mở slide menu bên thanh search
                else if (item.getItemId() == R.id.open_menu_slide) {
                    mDrawerLayout = findViewById(R.id.drawer_layout);
                    mDrawerLayout.openDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.create_live_stream) {
                    Intent createLive = new Intent(MainScreenActivity.this, CreateLiveActivity.class);
                    startActivity(createLive);
                }

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

    // interface response AsyncResponse
    public interface AsyncResponse {
        void processFinish(String output);
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
        protected String doInBackground(String... token) {
            Request request = HttpClient.buildGetRequest(Api.URL_GET_INFO, token[0]);
            return HttpClient.execute(request);
        }

        @Override
        protected void onPostExecute(String result) {
        asyncResponse.processFinish(result);
        }
    }

    public void doProcessNotLogin() {
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
        slide_view.getMenu().clear();
        slide_view.inflateHeaderView(R.layout.slide_header);
        slide_view.inflateMenu(R.menu.menu_slide);
        //  Thêm button logout vào slide khi da dang nhap
        btn_logout = slide_view.getHeaderView(0).findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slide_view.removeHeaderView(slide_view.getHeaderView(0));
                logout();
            }
        });
        TextView profile_fullname = slide_view.getHeaderView(0).findViewById(R.id.profile_fullname);
        profile_fullname.setText(user.getNickname());
        TextView profile_email = slide_view.getHeaderView(0).findViewById(R.id.profile_email);
        profile_email.setText(user.getGmail());
        if (user.getAvatar() != null && !user.getGmail().isEmpty()) {
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



