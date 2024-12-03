package com.example.m13_project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;

public class UserService {

    private static final String BASE_URL = "http://10.0.2.2:5000";
    private static final String TAG = "UserService";

    // Login method
    public String loginUser(String email, String password) {
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
}
