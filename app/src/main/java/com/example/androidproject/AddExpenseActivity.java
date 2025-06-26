package com.example.androidproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText titleInput, categoryInput, amountInput;
    private Button saveBtn;

    private DatabaseReference expenseRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        titleInput = findViewById(R.id.editTextTitle);
        categoryInput = findViewById(R.id.editTextCategory);
        amountInput = findViewById(R.id.editTextAmount);
        saveBtn = findViewById(R.id.buttonSave);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        expenseRef = FirebaseDatabase.getInstance().getReference("expenses").child(userId);

        saveBtn.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String category = categoryInput.getText().toString().trim();
            String amountStr = amountInput.getText().toString().trim();

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(category) || TextUtils.isEmpty(amountStr)) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Amount must be a valid number", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = expenseRef.push().getKey();
            long timestamp = System.currentTimeMillis();

            Expense expense = new Expense(id, title, category, amount, timestamp);

            expenseRef.child(id).setValue(expense)
                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Expense saved", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to save expense", Toast.LENGTH_SHORT).show());
        });
    }
}
