package com.example.kcaltracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.util.Log;
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
        ProgressBar progressBar = view.findViewById(R.id.Tracker_ProgressBar);
        int progress = 1500;
        int goal = 2000;
        new Thread(new Runnable() {
            int i = 1;
            public void run() {
                progressBar.setProgress(0);
                int normalizedProgress = normalizedProgress(progress,goal);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (i < normalizedProgress) {
                    i += 1;
                    progressBar.setProgress(i);
                    try {
                        Thread.sleep(0, 50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public int normalizedProgress(int progress, int goal){
        if (progress >= goal){
            return 2800;
        }
        float ratio = (float) progress / (float) goal;
        return (int) (ratio * 2800);
    }
}