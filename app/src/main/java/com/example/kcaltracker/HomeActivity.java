package com.example.kcaltracker;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.kcaltracker.model.ApiRequest;
import com.example.kcaltracker.model.DatabaseHelper;
import com.example.kcaltracker.model.FoodItem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {
    private String username;
    private String email;
    private String accessToken;
    private SearchItem searchItem = new SearchItem();
    private Tracker tracker = new Tracker();
    private FragmentTransaction fragmentTransaction;
    private Dialog myDialog;
    private  DatabaseHelper db;
    private FoodItem foodItem;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private ApiRequest apiRequest;
    private OkHttpClient client;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        selectedDate = dateFormat.format(new Date());

        apiRequest = new ApiRequest();
        settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = settings.edit();
        client = new OkHttpClient();
        setContentView(R.layout.activity_home_page);

        username = settings.getString("username", null);
        email = settings.getString("email", null);
        accessToken = settings.getString("accessToken", null);

        TextView text_username= findViewById(R.id.Home_UserName);
        text_username.setText(username);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Home_Frame, tracker);
        fragmentTransaction.commit();

        myDialog = new Dialog(this);
    }

    public void onSearch(View view) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Home_Frame, searchItem);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onMain(View view) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Home_Frame, tracker);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onLogout(View view) {
        client.newCall( apiRequest.logout(accessToken)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    editor.putString("username", null);
                    editor.putString("email", null);
                    editor.putString("accessToken",null);
                    editor.commit();
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                    finish();
                }
                response.close();
            }
        });
    }

    public void onAddFoodItem(View view){
        //Always first
        myDialog.setContentView(R.layout.popup_add_food);
        //
        CalendarView calendarView = (CalendarView) myDialog.findViewById(R.id.AddFood_Calendar_DatePicker);
        calendarView.setMaxDate(new Date().getTime());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Months are indexed from 0 in CalendarView (January is 0)
                // increment month for correct display or storage
                month += 1;
                selectedDate = String.valueOf(year)+ "-" + String.format("%02d",month) + "-" + String.format("%02d",dayOfMonth);
            }
        });

        FoodAutoSuggest foodAutoSuggest = (FoodAutoSuggest) myDialog.findViewById(R.id.AddFood_Input_FoodItem);
        ImageView closeButton = (ImageView) myDialog.findViewById(R.id.AddFood_Button_Close);
        ImageView addButton = (ImageView) myDialog.findViewById(R.id.AddFood_Button_Add);
        closeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view) {
                myDialog.dismiss();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view) {
                foodItem = foodAutoSuggest.getSelectedItem();
                EditText weightEditText = (EditText) myDialog.findViewById(R.id.AddFood_Input_Weight);
                String weight = weightEditText.getText().toString();
                Spinner type = (Spinner) myDialog.findViewById(R.id.AddFood_Input_Meal);
                if (foodItem == null || weight.isEmpty()){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    getApplicationContext(),
                                    R.string.missingFields,
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
                } else {
                    String[] foodType = {"Breakfast", "Lunch", "Afternoon Tea", "Dinner", "Supper"};
                    client.newCall(apiRequest.addHistory(
                            accessToken,
                            foodItem.id,
                            parseFloat(weight),
                            foodType[type.getAutofillValue().getListValue()],
                            selectedDate
                    )).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {}
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                myDialog.dismiss();
                                tracker.getProgress();
                            } else {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                R.string.genericError,
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }
                                });
                            }
                            response.close();
                        }
                    });
                }
            }
        });
        myDialog.show();
    }

    public void onEditGoal(View view){
        myDialog.setContentView(R.layout.popup_edit_goal);
        TextView currentGoal = (TextView) myDialog.findViewById(R.id.EditGoal_Text_CurrentGoal);
        ImageView closeButton = (ImageView) myDialog.findViewById(R.id.EditGoal_Button_Close);
        ImageView saveButton = (ImageView) myDialog.findViewById(R.id.EditGoal_Button_Set);
        EditText goalEditText = (EditText) myDialog.findViewById(R.id.EditGoal_Input_Goal);
        
        currentGoal.setText("Current Goal: " + settings.getInt("goal", 0));
        closeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view) {
                myDialog.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view) {
                String goal = goalEditText.getText().toString();
                if (goal.isEmpty()){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    getApplicationContext(),
                                    R.string.missingFields,
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
                } else {
                    client.newCall(apiRequest.editGoal(accessToken, parseInt(goal))).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {}
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                myDialog.dismiss();
                                editor.putInt("goal", parseInt(goal));
                                editor.commit();
                                tracker.getProgress();
                            } else {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                R.string.genericError,
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }
                                });
                            }
                            response.close();
                        }
                    });
                }
            }
        });
        myDialog.show();
    }

    public void onBack(View view){
        super.onBackPressed();
    }
}
