package com.example.a1212508_1211441_courseproject;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextFirstName, editTextLastName, editTextPassword, editTextConfirmPassword;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHelper = new DataBaseHelper(this);
        editTextEmail = findViewById(R.id.et_email);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPassword = findViewById(R.id.et_password);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button signUpButton = findViewById(R.id.btn_sign_up);

        signUpButton.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String firstName = editTextFirstName.getText().toString().trim();
            String lastName = editTextLastName.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (validateInputs(email, firstName, lastName, password, confirmPassword)) {
                if (dbHelper.registerUser(email, firstName, lastName, password)) {
                    Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "User already exists.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInputs(String email, String firstName, String lastName, String password, String confirmPassword) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Invalid email format.");
            return false;
        }
        if (firstName.length() < 5 || firstName.length() > 20) {
            editTextFirstName.setError("First name must be 5-20 characters.");
            return false;
        }
        if (lastName.length() < 5 || lastName.length() > 20) {
            editTextLastName.setError("Last name must be 5-20 characters.");
            return false;
        }
        if (password.length() < 6 || password.length() > 12 || !password.matches(".*\\d.*") ||
                !password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*")) {
            editTextPassword.setError("Password must be 6-12 characters, with at least one number, one lowercase, and one uppercase letter.");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Passwords do not match.");
            return false;
        }
        return true;
    }
}