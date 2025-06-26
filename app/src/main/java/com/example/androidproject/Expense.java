package com.example.androidproject;

public class Expense {
    private String id;
    private String title;
    private String category;
    private double amount;
    private long timestamp;

    public Expense() {
        // Required for Firebase
    }

    public Expense(String id, String title, String category, double amount, long timestamp) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
