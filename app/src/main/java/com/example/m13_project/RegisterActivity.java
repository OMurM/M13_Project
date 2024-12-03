package com.example.m13_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextPhone, editTextFirstName, editTextLastName;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the EditText fields
        editTextEmail = findViewById(R.id.editTextRegisterEmail);
        editTextPassword = findViewById(R.id.editTextRegisterPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    registerUser();
                } catch (Exception e) {
                    Log.e(TAG, "Exception during registration", e);
                    Toast.makeText(RegisterActivity.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button switchToLogin = findViewById(R.id.switchToLoginButton);
        switchToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email is required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password is required");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            editTextPhone.setError("Phone is required");
            return;
        }
        if (TextUtils.isEmpty(firstName)) {
            editTextFirstName.setError("First name is required");
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            editTextLastName.setError("Last name is required");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                UserService userService = new UserService();
                String response = userService.registerUser(email, password, phone, firstName, lastName);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response != null) {
                            Log.d(TAG, "Registration successful: " + response);
                            Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            // Switch to home page
                            Intent intent = new Intent(RegisterActivity.this, HomePageActivity.class);
                            startActivity(intent);
                        } else {
                            Log.e(TAG, "Registration failed");
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }
}
