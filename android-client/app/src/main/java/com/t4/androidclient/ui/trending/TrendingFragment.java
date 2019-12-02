package com.t4.androidclient.ui.trending;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import  com.t4.androidclient.R;

public class TrendingFragment extends Fragment {

    private TrendingViewModel trendingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        trendingViewModel =
                ViewModelProviders.of(this).get(TrendingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_trending, container, false);
        return root;
    }
}