package com.example.kcaltracker;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Tracker extends Fragment {
    private Handler hdlr = new Handler();
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
                while (i < normalizedProgress(1500,2000)) {

                    i += 1;
                    // Update the progress bar and display the current value in text view
                    hdlr.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(i);
                        }
                    });
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }



    public int normalizedProgress(int progress, int goal){
        float ratio = (float) progress / (float) goal;
        return (int) (ratio * 2800);
    }
}