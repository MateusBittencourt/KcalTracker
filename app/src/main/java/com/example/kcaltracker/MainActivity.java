package com.example.kcaltracker;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = "http://192.168.18.32:3000/User/login";
        OkHttpClient client = new OkHttpClient();

        String json = null;
        EditText username= findViewById(R.id.username);
        EditText password= findViewById(R.id.password);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username.getText().toString());
            jsonObject.put("password", password.getText().toString());

            json = jsonObject.toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(json, MEDIA_TYPE);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(
                                getApplicationContext(),
                                "Usuário ou senha incorretos",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Usuário ou senha incorretos",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
                } else {
                    String responseBody = response.body().string();
                    Log.d("response", responseBody);
                    JSONObject userObj = null;
                    String responseUsername;
                    String responseEmail;
                    try {
                        userObj = new JSONObject(responseBody);
                        responseUsername = userObj.getString("username");
                        responseEmail = userObj.getString("email");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    Intent homeActivity = new Intent(MainActivity.this, HomeActivity.class);
                    homeActivity.putExtra("username", responseUsername);
                    homeActivity.putExtra("email", responseEmail);
                    startActivity(homeActivity);
                    finish();
                }
            }
        });
    }

    public void onCreateAccount(View view) {
        Intent createAccount = new Intent(this,
                CreateAccountActivity.class);
        startActivity(createAccount);
        finish();
    }

    public void onRecoverPassword(View view) {
        Intent recoverPassword = new Intent(this,
                RecoverPasswordActivity.class);
        startActivity(recoverPassword);
        finish();
    }
}

