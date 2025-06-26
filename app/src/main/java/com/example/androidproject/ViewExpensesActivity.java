package com.example.androidproject;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class ViewExpensesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private final List<Expense> expenseList = new ArrayList<>();
    private DatabaseReference expenseRef;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);

        recyclerView = findViewById(R.id.recyclerViewExpenses);
        totalTextView = findViewById(R.id.textViewTotal);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        expenseRef = FirebaseDatabase.getInstance().getReference("expenses").child(userId);

        adapter = new ExpenseAdapter(this, expenseList, this::deleteExpense);
        recyclerView.setAdapter(adapter);

        loadExpenses();
    }

    private void loadExpenses() {
        expenseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                expenseList.clear();
                double total = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Expense expense = dataSnapshot.getValue(Expense.class);
                    if (expense != null) {
                        expenseList.add(expense);
                        total += expense.getAmount();
                    }
                }

                adapter.notifyDataSetChanged();
                totalTextView.setText("Total: Rs. " + total);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewExpensesActivity.this, "Error loading expenses", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteExpense(Expense expense) {
        expenseRef.child(expense.getId()).removeValue()
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show());
    }
}