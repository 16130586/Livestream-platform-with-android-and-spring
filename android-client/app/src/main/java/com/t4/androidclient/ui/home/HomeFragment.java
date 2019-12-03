package com.t4.androidclient.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import  com.t4.androidclient.R;
import com.t4.androidclient.model.livestream.LiveStream;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private VideoView clip_ads;
    private ListView listView;
    private ArrayList<LiveStream> listLiveStream;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        clip_ads = root.findViewById(R.id.clip_ads);
        Uri videoUrl = Uri.parse("android.resource://"+ getActivity().getApplicationContext().getPackageName() +"/"+R.raw.clip_ads_example);
        clip_ads.setVideoURI(videoUrl);
//        clip_ads.requestFocus();
//        clip_ads.start();
        // thêm object live stream





        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}