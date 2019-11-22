package com.t4.androidclient.ui.subscription;

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

public class SubscriptionFragment extends Fragment {

    private SubscriptionViewModel subscriptionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        subscriptionViewModel =
                ViewModelProviders.of(this).get(SubscriptionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_subscription, container, false);
        final TextView textView = root.findViewById(R.id.text_subscription);
        subscriptionViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}