package com.example.m13_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserService {

    private static final String BASE_URL = "http://10.0.2.2:5000";
    private static final String TAG = "UserService";
    private SharedPreferences sharedPreferences;

    public UserService(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    // Login method
    public String loginUser(String email, String password, boolean rememberMe) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(BASE_URL + "/login");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = String.format(
                    "{\"email\": \"%s\", \"password\": \"%s\"}",
                    email, password
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            Log.d(TAG, "Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the response to get the tokens
                JSONObject jsonResponse = new JSONObject(response.toString());
                String accessToken = jsonResponse.getString("access_token");
                String refreshToken = jsonResponse.getString("refresh_token");

                // Store tokens in SharedPreferences if remember me is checked
                if (rememberMe) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("accessToken", accessToken);
                    editor.putString("refreshToken", refreshToken);
                    editor.apply();
                }

                return response.toString();
            } else {
                Log.e(TAG, "Login request failed with response code: " + responseCode);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception during login", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    // Register method
    public String registerUser(String email, String password, String phone, String firstName, String lastName) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(BASE_URL + "/register");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = String.format(
                    "{\"email\": \"%s\", \"password\": \"%s\", \"phone\": \"%s\", \"first_name\": \"%s\", \"last_name\": \"%s\"}",
                    email, password, phone, firstName, lastName
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            Log.d(TAG, "Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                Log.e(TAG, "Register request failed with response code: " + responseCode);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception during registration", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    // Refresh token method
    public String refreshToken() {
        HttpURLConnection conn = null;
        try {
            String refreshToken = sharedPreferences.getString("refreshToken", null);
            if (refreshToken == null) {
                Log.e(TAG, "No refresh token found");
                return null;
            }

            URL url = new URL(BASE_URL + "/refresh");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + refreshToken);
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            Log.d(TAG, "Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the response to get the new access token
                JSONObject jsonResponse = new JSONObject(response.toString());
                String newAccessToken = jsonResponse.getString("access_token");

                // Store the new access token in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("accessToken", newAccessToken);
                editor.apply();

                return response.toString();
            } else {
                Log.e(TAG, "Refresh token request failed with response code: " + responseCode);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception during refresh token", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    // Get access token from SharedPreferences
    public String getAccessToken() {
        return sharedPreferences.getString("accessToken", null);
    }
}