package com.example.kcaltracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kcaltracker.model.ApiRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences settings;
    private  SharedPreferences.Editor editor;
    private OkHttpClient client;
    private MediaType MEDIA_TYPE;
    private ApiRequest apiRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiRequest = new ApiRequest();
        settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = settings.edit();

        client = new OkHttpClient();
        MEDIA_TYPE = MediaType.parse("application/json");
        logginByToken();
    }

    public void onCreateAccount(View view) {
        Intent createAccount = new Intent(this, CreateAccountActivity.class);
        startActivity(createAccount);
        finish();
    }

    public void onRecoverPassword(View view) {
        Intent recoverPassword = new Intent(this, RecoverPasswordActivity.class);
        startActivity(recoverPassword);
        finish();
    }

    public void login(View view) {
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        client.newCall(apiRequest.login(
                username.getText().toString(),
                password.getText().toString()
        )).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    successfulLogin(response.body().string());
                } else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    getApplicationContext(),
                                    R.string.wrong_UserPassword,
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
                }
                response.close();
            }
        });
    }

    private void logginByToken() {
        String accessToken = settings.getString("accessToken", null);
        client.newCall(apiRequest.loginByToken(accessToken)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    successfulLogin(response.body().string());
                }
                response.close();
            }
        });
    }

    private void successfulLogin (String responseBody){
        JSONObject userObj = null;
        String responseUsername;
        String responseEmail;
        String responseToken;
        try {
            userObj = new JSONObject(responseBody);
            responseUsername = userObj.getString("username");
            responseEmail = userObj.getString("email");
            responseToken = userObj.getString("accessToken");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        editor.putString("username", responseUsername);
        editor.putString("email", responseEmail);
        editor.putString("accessToken",responseToken);
        editor.commit();
        Intent homeActivity = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(homeActivity);
        finish();
    }
}