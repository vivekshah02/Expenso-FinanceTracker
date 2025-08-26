package com.financetracker.repository;

import com.financetracker.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

//transactions operations
@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    
    //find all transactions for a specific user
    List<Transaction> findByUserIdOrderByDateDesc(String userId);
    
    //find transactions by user and type (income or expense)
    List<Transaction> findByUserIdAndType(String userId, Transaction.TransactionType type);
}