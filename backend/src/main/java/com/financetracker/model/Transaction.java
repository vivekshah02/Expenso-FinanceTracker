package com.financetracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;
    
    private String userId;  
    private TransactionType type;  // income or expense
    private double amount;
    private String category;  // already defined
    private String description;  // Optional 
    private LocalDateTime date;  // Transaction date

    public enum TransactionType {
        INCOME, EXPENSE
    }

    public Transaction() {}

    // creating new transactions
    public Transaction(String userId, TransactionType type, double amount, String category, String description) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}