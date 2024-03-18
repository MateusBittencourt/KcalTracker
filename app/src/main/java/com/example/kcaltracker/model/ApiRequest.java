package com.example.kcaltracker.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiRequest {
    private MediaType MEDIA_TYPE;
    private String backendAddress;
    private String jsonString;
    private RequestBody body;
    public ApiRequest () {
        MEDIA_TYPE = MediaType.parse("application/json");
        backendAddress = "http://15.229.220.141:3000/";
    }

    private Request buildRequest (String url, JSONObject jsonObject){
        String jsonString = jsonObject.toString();
        body = RequestBody.create(jsonString, MEDIA_TYPE);
        return new Request.Builder()
                .url(backendAddress.concat(url))
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();
    }

    public Request login (String username, String password){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException exception) { }
        return buildRequest("user/login", jsonObject);
    }
    public Request loginByToken (String token) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("accessToken", token);
        } catch (JSONException exception) { }
        return buildRequest("user/loginByToken", jsonObject);
    }
    public Request logout (String token) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("accessToken", token);
        } catch (JSONException exception) { }
        return buildRequest("user/logout", jsonObject);
    }
    public Request passwordChange (String username, String currentPassword, String newPassword) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("currentPassword", currentPassword);
            jsonObject.put("newPassword", newPassword);
        } catch (JSONException exception) { }
        return buildRequest("user/passwordChange", jsonObject);
    }
    public Request passwordRecovery (String email) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("email", email);
        } catch (JSONException exception) { }
        return buildRequest("user/passwordRecovery", jsonObject);
    }
    public Request passwordInputToken (String validationToken, String password) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("validationToken", validationToken);
            jsonObject.put("password", password);
        } catch (JSONException exception) { }
        return buildRequest("user/passwordInputToken", jsonObject);
    }
    public Request getHistory (String accessToken, String date) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("accessToken", accessToken);
            jsonObject.put("date", date);
        } catch (JSONException exception) { }
        return buildRequest("history/get", jsonObject);
    }
    public Request addHistory (String accessToken, int foodId, float weight, String meal, String date) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("accessToken", accessToken);
            jsonObject.put("foodId", foodId);
            jsonObject.put("weight", weight);
            jsonObject.put("meal", meal);
            jsonObject.put("date", date);
        } catch (JSONException exception) { }
        return buildRequest("history/add", jsonObject);
    }
    public Request removeHistory (String accessToken, int historyId) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("accessToken", accessToken);
            jsonObject.put("historyId", historyId);
        } catch (JSONException exception) { }
        return buildRequest("history/remove", jsonObject);
    }
}
