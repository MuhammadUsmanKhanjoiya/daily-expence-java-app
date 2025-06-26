package com.example.androidproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    TextView welcomeText;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcomeText = findViewById(R.id.welcomeText);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            welcomeText.setText("Welcome, " + user.getEmail());
        } else {
            welcomeText.setText("Welcome, Guest!");
        }
    }

    public void logoutUser(View view) {
        mAuth.signOut();
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        finish();
    }

    public void goToAddExpense(View view) {
        startActivity(new Intent(HomeActivity.this, AddExpenseActivity.class));
    }

    public void goToViewExpenses(View view) {
        startActivity(new Intent(HomeActivity.this, ViewExpensesActivity.class));
    }
}
