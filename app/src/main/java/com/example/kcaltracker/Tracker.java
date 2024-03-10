package com.example.kcaltracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class Tracker extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tracker, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //ProgressBar progressBar = view.findViewById(R.id.Tracker_ProgressBar);
    }
}