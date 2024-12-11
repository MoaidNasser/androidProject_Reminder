package com.example.a1212508_1211441_courseproject;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private CheckBox checkBoxRememberMe;
    private DataBaseHelper dbHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        dbHelper = new DataBaseHelper(this);

        // Initialize UI elements
        editTextEmail = findViewById(R.id.et_email);
        editTextPassword = findViewById(R.id.et_password);
        checkBoxRememberMe = findViewById(R.id.cb_remember_me);
        Button loginButton = findViewById(R.id.btn_sign_in);
        Button signUpButton = findViewById(R.id.btn_sign_up);

        // Load saved credentials if "Remember Me" was checked last time
        loadRememberedCredentials();

        // Set listeners for buttons
        setUpButtonListeners(loginButton, signUpButton);
    }

    /**
     * Set listeners for login and sign up buttons.
     */
    private void setUpButtonListeners(Button loginButton, Button signUpButton) {
        // Login Button
        loginButton.setOnClickListener(v -> handleLogin());

        // Sign Up Button
        signUpButton.setOnClickListener(v -> openSignUpActivity());
    }

    /**
     * Handle login functionality.
     */
    private void handleLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.authenticateUser(email, password)) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

            // Save credentials if "Remember Me" is checked
            if (checkBoxRememberMe.isChecked()) {
                saveCredentials(email, password);
            } else {
                clearCredentials();
            }

            ////////////////////////////////////////////////////////////////////////////////////
            // Redirect to HomeActivity
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Navigate to the SignUpActivity.
     */
    private void openSignUpActivity() {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    /**
     * Load saved credentials from SharedPreferences.
     */
    private void loadRememberedCredentials() {
        String email = sharedPreferences.getString("email", null);
        String password = sharedPreferences.getString("password", null);

        if (email != null && password != null) {
            editTextEmail.setText(email);
            editTextPassword.setText(password);
            checkBoxRememberMe.setChecked(true);
        }
    }

    /**
     * Save credentials to SharedPreferences.
     */
    private void saveCredentials(String email, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    /**
     * Clear saved credentials from SharedPreferences.
     */
    private void clearCredentials() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.apply();
    }
}