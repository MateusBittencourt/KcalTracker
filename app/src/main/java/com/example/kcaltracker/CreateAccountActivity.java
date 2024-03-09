package com.example.kcaltracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText editTextUser;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRepassword;
    private TextView warningText;
    private MediaType MEDIA_TYPE = MediaType.parse("application/json");
    private String url = "http://192.168.18.32:3000/User";
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        warningText = findViewById(R.id.CreateAccount_Text_warning);
        warningText.setVisibility(View.GONE);
        editTextPassword = findViewById(R.id.CreateAccount_password);
        editTextRepassword = findViewById(R.id.repassword_createAccount);
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void afterTextChanged(Editable s) {
                passwordMatch();
            }
        });
        editTextRepassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void afterTextChanged(Editable s) {
                passwordMatch();
            }
        });
    }

    public void goBack(View view) {
        Intent mainActivity = new Intent(this,
                MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    public void onCreateAccount(View view) {
        editTextUser = findViewById(R.id.CreateAccount_username);
        editTextEmail = findViewById(R.id.CreateAccount_email);
        editTextPassword = findViewById(R.id.CreateAccount_password);
        editTextRepassword = findViewById(R.id.repassword_createAccount);

        warningText = findViewById(R.id.CreateAccount_Text_warning);

        String username = editTextUser.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String repassword = editTextRepassword.getText().toString();

        if (!isEmailValid(email)) {
            warningText.setText(R.string.validEmail);
            warningText.setVisibility(View.VISIBLE);
        } else {
            warningText.setText("");
            warningText.setVisibility(View.GONE);
            if (password.equals(repassword)) {
                if (password.length() >= 8) {
                    warningText.setText("");
                    warningText.setVisibility(View.GONE);

                    String json = null;
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("username", username);
                        jsonObject.put("email", email);
                        jsonObject.put("password", password);
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

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseBody = response.body().string();
                            if (!response.isSuccessful()) {
                                if (responseBody.contains(("username unavailable"))){
                                    warningText.setText(R.string.usernameUnavailable);
                                    warningText.setVisibility(View.VISIBLE);
                                } else if (responseBody.contains(("email already registered"))){
                                    warningText.setText(R.string.emailAlreadyRegistered);
                                    warningText.setVisibility(View.VISIBLE);
                                }
                            } else {
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
                                Intent homeActivity = new Intent(CreateAccountActivity.this, HomeActivity.class);
                                homeActivity.putExtra("username", responseUsername);
                                homeActivity.putExtra("email", responseEmail);
                                startActivity(homeActivity);
                                finish();
                            }
                        }
                    });
                } else {
                    warningText.setText(R.string.shortPassword);
                    warningText.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private boolean isEmailValid(String email) {
        String regEx = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return Pattern.compile(regEx).matcher(email.trim()).matches();
    }
    private void passwordMatch() {
        if (!editTextPassword.getText().toString().equals(editTextRepassword.getText().toString())) {
            warningText.setText(R.string.mismatchPassword);
            warningText.setVisibility(View.VISIBLE);
        } else {
            warningText.setText("");
            warningText.setVisibility(View.GONE);
        }
    }
}
