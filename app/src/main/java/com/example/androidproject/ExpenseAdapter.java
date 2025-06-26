package com.example.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    public interface OnDeleteClickListener {
        void onDeleteClick(Expense expense);
    }

    private final Context context;
    private final List<Expense> expenseList;
    private final OnDeleteClickListener deleteClickListener;

    public ExpenseAdapter(Context context, List<Expense> expenseList, OnDeleteClickListener deleteClickListener) {
        this.context = context;
        this.expenseList = expenseList;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.titleTextView.setText(expense.getTitle());
        holder.categoryTextView.setText("Category: " + expense.getCategory());
        holder.amountTextView.setText("Amount: Rs " + expense.getAmount());
        holder.deleteButton.setOnClickListener(v -> deleteClickListener.onDeleteClick(expense));
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, categoryTextView, amountTextView;
        Button deleteButton;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.expenseTitle);
            categoryTextView = itemView.findViewById(R.id.expenseCategory);
            amountTextView = itemView.findViewById(R.id.expenseAmount);
            deleteButton = itemView.findViewById(R.id.btnDelete);
        }
    }
}
