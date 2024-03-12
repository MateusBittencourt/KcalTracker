package com.example.kcaltracker;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.kcaltracker.model.DatabaseHelper;
import com.example.kcaltracker.model.FoodItem;

import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    private ImageView profilePicture;
    private Button syncButton;
    private TextView welcomeMessage;
    private String username;
    private String email;
    private SearchItem searchItem = new SearchItem();
    private Tracker tracker = new Tracker();
    private FragmentTransaction fragmentTransaction;
    private Dialog myDialog;
    private  DatabaseHelper db;
    private FoodItem foodItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        username = String.valueOf(intent.getStringExtra("username"));
        email = String.valueOf(intent.getStringExtra("email"));

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

    public void onAddFoodItem(View view){
        //Always first
        myDialog.setContentView(R.layout.popup_add_food);
        //
        CalendarView calendarView = (CalendarView) myDialog.findViewById(R.id.AddFood_Calendar_DatePicker);
        calendarView.setMaxDate(new Date().getTime());
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
                Log.d("TestAddFood", foodItem.descricao);
                myDialog.dismiss();
            }
        });
        myDialog.show();;

    }

    public void onBack(View view){
        super.onBackPressed();
    }
}
