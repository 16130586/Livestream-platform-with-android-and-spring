package com.t4.androidclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.t4.androidclient.R;
import com.t4.androidclient.config.FacebookConfig;
import com.t4.androidclient.model.helper.FacebookOptionHelper;
import com.t4.androidclient.model.livestream.group.Group;
import com.t4.androidclient.model.livestream.page.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FacebookOptionActivity extends Activity {

    private List<String> testPage, testGroup, testList;
    private List<String> pageStringList;

    private Spinner fbOption, fbOptionPage, fbOptionGroup;

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<Page> pageAdapter;
    private ArrayAdapter<Group> groupAdapter;

    private LinearLayout groupView, pageView;
    private Button btnSave;

    private List<Page> pageList;
    private List<Group> groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_live_facebook_option);
        test();
        getPageAndGroup();
    }

    public void test() {
        testList = new ArrayList<>();
        testList.add("Personal");
        testList.add("Group");
        testList.add("Page");

        testPage = new ArrayList<>();
        testPage.add("page 1");
        testPage.add("page 2");

        testGroup = new ArrayList<>();
        testGroup.add("g1");
        testGroup.add("g2");
    }

    public void setUp() {
        groupView = findViewById(R.id.view_fb_option_group);
        pageView = findViewById(R.id.view_fb_option_page);

        adapter =  new ArrayAdapter<String>(this, R.layout.facebook_option_spinner_item, testList);
        adapter.setDropDownViewResource(R.layout.facebook_option_spinner_item);

        pageAdapter = new ArrayAdapter<Page>(this, R.layout.facebook_option_spinner_item, pageList);
        pageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        groupAdapter = new ArrayAdapter<Group>(this, R.layout.facebook_option_spinner_item, groupList);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fbOption = findViewById(R.id.fb_option);
        fbOption.setAdapter(adapter);
        fbOptionPage = findViewById(R.id.fb_option_f_page);
        fbOptionPage.setAdapter(pageAdapter);
        fbOptionGroup = findViewById(R.id.fb_option_f_group);
        fbOptionGroup.setAdapter(groupAdapter);

        fbOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String option = fbOption.getSelectedItem().toString();
                if ("group".equalsIgnoreCase(option)) {
                    groupView.setVisibility(View.VISIBLE);
                    pageView.setVisibility(View.GONE);
                } else if ("page".equalsIgnoreCase(option)) {
                    groupView.setVisibility(View.GONE);
                    pageView.setVisibility(View.VISIBLE);
                } else {
                    groupView.setVisibility(View.GONE);
                    pageView.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        btnSave = findViewById(R.id.btn_fb_option_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveOption();
            }
        });
    }

    public void getPageAndGroup() {
        String accessToken = getIntent().getStringExtra("ACCESS_TOKEN");

        GetFaceBookPageAndGroup getFaceBookPage = new GetFaceBookPageAndGroup(new AsyncResponse() {
            @Override
            public void processFinish(List<String> output) {
                FacebookOptionHelper helper = new FacebookOptionHelper();
                pageList = helper.parseFacebookPage(output.get(0));
                System.out.println(pageList.toString());

                groupList = helper.parseFacebookGroup(output.get(1));
                System.out.println(groupList.toString());
                setUp();
            }
        });
        getFaceBookPage.execute(accessToken);

    }

    // interface AsyncResponse
    public interface AsyncResponse {
        void processFinish(List<String> output);
    }

    private class GetFaceBookPageAndGroup extends AsyncTask<String, Integer, List<String>> {
        public AsyncResponse asyncResponse = null;


        // constructor
        public GetFaceBookPageAndGroup(AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected List<String> doInBackground(String... tokens) {
            ArrayList<String> result = new ArrayList<>();
            String accessToken = tokens[0];
            String url = FacebookConfig.V5_BASE_URL + "me/accounts?access_token=" + accessToken + "&limit=5000";

            System.out.println("url group: "  + url);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                String rs = response.body().string();
                result.add(rs);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            String url2 = FacebookConfig.V5_BASE_URL + "me/groups?access_token="
                    + accessToken + "&limit=5000";
            request = new Request.Builder().url(url2).build();
            try (Response response = client.newCall(request).execute()) {
                String rs = response.body().string();
                result.add(rs);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            asyncResponse.processFinish(result);
        }
    }

    public void saveOption() {
        String option = fbOption.getSelectedItem().toString();
        String id = null;
        if ("page".equalsIgnoreCase(option)) {
            id = getPageId(fbOptionPage.getSelectedItem().toString());
        }
        if ("group".equalsIgnoreCase(option)) {
            id = getGroupId(fbOptionGroup.getSelectedItem().toString());
        }
        Intent returnIntent = new Intent();
        returnIntent.putExtra("OPTION", option);
        returnIntent.putExtra("ID", id);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public String getPageId(String name) {
        for (int i = 0; i < pageList.size(); i++) {
            if (pageList.get(i).getName().equals(name)){
                return pageList.get(i).getId();
            }
        }
        return null;
    }

    public String getGroupId(String name) {
        for (int i = 0; i < groupList.size(); i++) {
            if (groupList.get(i).getName().equals(name)){
                return groupList.get(i).getId();
            }
        }
        return null;
    }
}
