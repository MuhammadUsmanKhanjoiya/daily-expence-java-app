package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private FirebaseAuth mAuth;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Firebase initialize
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        // Find views
        emailField = findViewById(R.id.registerEmail);
        passwordField = findViewById(R.id.registerPassword);
    }

    public void registerUser(View view) {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Email aur password required hai", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password kam az kam 6 characters ka hona chahiye", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase register
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    } else {
                        String error = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Log.e(TAG, "Firebase Error: " + error);
                        Toast.makeText(RegisterActivity.this, "Failed: " + error, Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void goBackToLogin(View view) {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }
}
