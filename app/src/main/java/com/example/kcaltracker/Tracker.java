package com.example.kcaltracker;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kcaltracker.model.ApiRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class Tracker extends Fragment {
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private ApiRequest apiRequest;
    private String accessToken;
    private OkHttpClient client;
    private int caloriesTotal;
    ProgressBar progressBar;
    TextView uiProgress;
    TextView uiGoal;
    View divider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tracker, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        client = new OkHttpClient();
        apiRequest = new ApiRequest();
        settings = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        progressBar = view.findViewById(R.id.Tracker_ProgressBar);
        uiProgress = view.findViewById(R.id.Tracker_Text_Current);
        uiGoal = view.findViewById(R.id.Tracker_Text_Goal);
        divider = view.findViewById(R.id.Tracker_CounterDivider);
        accessToken = settings.getString("accessToken", null);
        editor = settings.edit();
        getProgress();
    }

    private int normalizedProgress(int progress, int goal){
        if (progress >= goal){
            return 2800;
        }
        float ratio = (float) progress / (float) goal;
        return (int) (ratio * 2800);
    }

    public void getProgress (){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        caloriesTotal = 0;
        client.newCall(apiRequest.getHistory(accessToken, date)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String temp = response.body().string();
                    JSONArray jsonArray;
                    ArrayList<JSONObject> jsonObjects = new ArrayList<JSONObject>();

                    try {
                        jsonArray = new JSONArray(temp);
                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonObjects.add(jsonArray.getJSONObject(i));
                            double weight = jsonObjects.get(i).getDouble("weight");
                            double kcal = jsonObjects.get(i).getDouble("kcal");
                            caloriesTotal += (int) Math.round(weight * kcal * 0.01);
                        }
                        Log.d("Body", String.valueOf(jsonObjects));
                        Log.d("Kcal", String.valueOf(caloriesTotal));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                updateProgressBar();
                response.close();
            }
        });
    }

    private void updateProgressBar() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int progress = caloriesTotal;
                uiProgress.setText(String.valueOf(progress + " Kcal"));
                int goal = settings.getInt("goal", 0);
                Log.d("Tracker", String.valueOf(goal));
                if (goal == 0){
                    uiGoal.setText("Calories");
                    divider.setVisibility(View.GONE);
                } else {
                    divider.setVisibility(View.VISIBLE);
                    uiGoal.setText(String.valueOf(goal + " Kcal"));

                    new Thread(new Runnable() {
                        int i = 1;
                        public void run() {
                            progressBar.setProgress(0);
                            int normalizedProgress = normalizedProgress(progress,goal);
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
            }
        });
    }
}