package com.example.m13_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private CheckBox checkBoxRememberMe;
    private static final String TAG = "MainActivity";
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonGoToRegister = findViewById(R.id.buttonGoToRegister);
        userService = new UserService(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loginUser();
                } catch (Exception e) {
                    Log.e(TAG, "Exception during login", e);
                    Toast.makeText(MainActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        boolean rememberMe = checkBoxRememberMe.isChecked();

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password is required");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = userService.loginUser(email, password, rememberMe);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response != null) {
                            Log.d(TAG, "Login successful: " + response);
                            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e(TAG, "Login failed");
                            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }
}