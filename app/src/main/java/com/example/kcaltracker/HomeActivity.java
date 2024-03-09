package com.example.kcaltracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.kcaltracker.model.DatabaseHelper;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {
    private ImageView profilePicture;
    private Button syncButton;
    private TextView welcomeMessage;
    private String username;
    private String email;

    private DatabaseHelper db;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        username = String.valueOf(intent.getStringExtra("username"));
        email = String.valueOf(intent.getStringExtra("email"));

        TextView text_username= findViewById(R.id.Home_UserName);
        text_username.setText(username);

        SearchItem searchItem = new SearchItem();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.Home_Frame, searchItem);
        fragmentTransaction.commit();
    }
}
