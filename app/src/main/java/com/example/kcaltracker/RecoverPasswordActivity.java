package com.example.kcaltracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kcaltracker.model.ApiRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecoverPasswordActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextToken;
    private EditText editTextPassword;
    private EditText editTextRepassword;
    TextView warningTextEmail;
    TextView warningTextPassword;
    private MediaType MEDIA_TYPE = MediaType.parse("application/json");
    private OkHttpClient client = new OkHttpClient();
    private ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiRequest = new ApiRequest();
        setContentView(R.layout.activity_recover_password);

        warningTextPassword  = findViewById(R.id.RecoverPassword_Text_warningPassword);
        warningTextEmail = findViewById(R.id.RecoverPassword_Text_warningEmail);

        warningTextEmail.setVisibility(View.GONE);
        warningTextPassword.setVisibility(View.GONE);

        editTextPassword = findViewById(R.id.RecoverPassword_Password);
        editTextRepassword = findViewById(R.id.RecoverPassword_Repassword);
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

    public void onRequestToken(View view) {

        editTextEmail = findViewById(R.id.RecoverPassword_Email);
        String email = editTextEmail.getText().toString();


        if (!isEmailValid(email)) {
            warningTextEmail.setText(R.string.validEmail);
            warningTextEmail.setVisibility(View.VISIBLE);
        } else {
            warningTextEmail.setText("");
            warningTextEmail.setVisibility(View.GONE);
            client.newCall(apiRequest.passwordRecovery(email)).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) { }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    getApplicationContext(),
                                    R.string.tokenSent,
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
                    response.close();
                }
            });
        }
    }

    public void onSendToken(View view) {
        editTextToken = findViewById(R.id.RecoverPassword_Token);
        String token = editTextToken.getText().toString();
        String password = editTextPassword.getText().toString();
        String repassword = editTextRepassword.getText().toString();

        if (password.equals(repassword)) {
            if (password.length() >= 8) {
                warningTextPassword.setText("");
                warningTextPassword.setVisibility(View.GONE);

                client.newCall(apiRequest.passwordInputToken(token, password)).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseBody = response.body().string();
                        if (response.isSuccessful()) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            R.string.successChangePassword,
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            });;
                            Intent mainActivity = new Intent(RecoverPasswordActivity.this, MainActivity.class);
                            startActivity(mainActivity);
                            finish();
                        } else {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            R.string.errorChangingPassword,
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            });
                        }
                        response.close();
                    }
                });
            } else {
                warningTextPassword.setText(R.string.shortPassword);
                warningTextPassword.setVisibility(View.VISIBLE);
            }
        }
    }

    private boolean isEmailValid(String email) {
        String regEx = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return Pattern.compile(regEx).matcher(email.trim()).matches();
    }
    private void passwordMatch() {
        if (!editTextPassword.getText().toString().equals(editTextRepassword.getText().toString())) {
            warningTextPassword.setText(R.string.mismatchPassword);
            warningTextPassword.setVisibility(View.VISIBLE);
        } else {
            warningTextPassword.setText("");
            warningTextPassword.setVisibility(View.GONE);
        }
    }
}
