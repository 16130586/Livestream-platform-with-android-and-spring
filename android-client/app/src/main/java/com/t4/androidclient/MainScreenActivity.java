package com.t4.androidclient;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.t4.androidclient.contraints.Authentication;
import com.t4.androidclient.httpclient.SqliteAuthenticationHelper;
import com.t4.androidclient.searching.MakeSuggestion;
import com.t4.androidclient.searching.Suggestion;
import com.t4.androidclient.searching.asyn;
import com.t4.androidclient.ui.login.LoginRegisterActivity;

import java.util.ArrayList;
import java.util.List;


public class MainScreenActivity extends AppCompatActivity implements MakeSuggestion {
    FloatingSearchView mSearchView;
    DrawerLayout mDrawerLayout;
    ImageView app_logo;
    MakeSuggestion makeSuggestion = this;
    NavigationView slide_view;
    private asyn a = null;
    private LoginButton mBtnFacebook;
    private CallbackManager mCallbackManager;
    private Activity current = this;
    private Button btn_login ;
    private Button btn_logout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mCallbackManager = CallbackManager.Factory.create();
        ///////////// thêm slide menu navigation
        // Kiểm tra session đã đăng nhập thì slide menu khác
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        // Set token
        setToken();
        //
        mDrawerLayout = findViewById(R.id.drawer_layout);
        slide_view = findViewById(R.id.slide_view);
//


        if (isLoggedIn==true) {
            slide_view.getMenu().clear();
            slide_view.inflateHeaderView(R.layout.slide_header);
            slide_view.inflateMenu(R.menu.menu_slide);
            System.out.println("Đã đăng nhập");
            //button loginFB khi da dang nhap
            // mBtnFacebook = slide_view.getHeaderView(0).findViewById(R.id.btn_logout_facebook);
//            btn_logout =  slide_view.getHeaderView(0).findViewById(R.id.btn_logout);
            btn_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        System.out.println("Xử lý đăng xuất chưa làm");
                }
            });
            TextView profile_fullname = slide_view.getHeaderView(0).findViewById(R.id.profile_fullname);
            profile_fullname.setText("Họ tên : + ten user ở đây ");
        }else {
            slide_view.getMenu().clear();
            slide_view.inflateHeaderView(R.layout.slide_header_not_login);
            System.out.println("Chưa đăng nhập");

            //button loginFB khi chua dang nhap
            // mBtnFacebook = slide_view.getHeaderView(0).findViewById(R.id.btn_login);
//cai nay phai connfirm voi tuan lai xem bi thieu j ma thieu loi k tim thay btn_logout            btn_login = slide_view.getHeaderView(0).findViewById(R.id.btn_login);
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Xử lý đăng nhập chưa làm");
                    finish();
                    Intent intent = new Intent(MainScreenActivity.this, LoginRegisterActivity.class);
                    startActivity(intent);
                }
            });
        }
//        Code LoginFB button
//        mBtnFacebook.setPermissions("user_location", "publish_video" , "user_events" , "manage_pages");
//        mBtnFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                AccessToken accessToken = AccessToken.getCurrentAccessToken();
//                LoginManager.getInstance().logInWithReadPermissions(current , Arrays.asList("user_events" , "manage_pages"));
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, accessToken.getToken());
//                sendIntent.setType("text/plain");
//                Intent shareIntent = Intent.createChooser(sendIntent, "Chọn gì nào");
//                startActivity(shareIntent);
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException e)
//            {
//
//            }
//        });


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
                        AccessToken accessToken = AccessToken.getCurrentAccessToken();
                        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                        if (isLoggedIn==true) {
                            slide_view.getMenu().clear();
                            slide_view.removeHeaderView(slide_view.getHeaderView(0));
                            slide_view.inflateHeaderView(R.layout.slide_header);
                            slide_view.inflateMenu(R.menu.menu_slide);
                            // FB button logout khi da dang nhap
                            // mBtnFacebook = slide_view.getHeaderView(0).findViewById(R.id.btn_logout_facebook);

                        }else {
                            slide_view.getMenu().clear();
                            slide_view.removeHeaderView(slide_view.getHeaderView(0));
                            slide_view.inflateHeaderView(R.layout.slide_header_not_login);
//       cai nay phai connfirm voi tuan lai xem bi thieu j ma thieu loi k tim thay btn_logout                    btn_login = slide_view.getHeaderView(0).findViewById(R.id.btn_login);
                            btn_login.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    System.out.println("Xử lý đăng nhập chưa làm");
                                    Intent intent = new Intent(MainScreenActivity.this, LoginRegisterActivity.class);
                                    startActivity(intent);
                                }
                            });
                            // FB button logout khi chua dang nhap
                           // mBtnFacebook = slide_view.getHeaderView(0).findViewById(R.id.btn_login);

                        }
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

}
    ///////////// Thêm slide menu navigation



