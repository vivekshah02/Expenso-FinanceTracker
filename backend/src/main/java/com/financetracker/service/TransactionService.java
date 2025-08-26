package com.financetracker.service;

import com.financetracker.model.Transaction;
import com.financetracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//Transaction service for managing income and expense transactions
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    //get all transactions for a user, ordered by date desc ie newest first
    public List<Transaction> getUserTransactions(String userId) {
        return transactionRepository.findByUserIdOrderByDateDesc(userId);
    }


    public Optional<Transaction> getTransactionById(String id) {
        return transactionRepository.findById(id);
    }


    public Transaction updateTransaction(String id, Transaction updatedTransaction) {
        Optional<Transaction> existingTransaction = transactionRepository.findById(id);
        
        if (existingTransaction.isEmpty()) {
            throw new RuntimeException("Transaction not found");
        }

        Transaction transaction = existingTransaction.get();
        transaction.setType(updatedTransaction.getType());
        transaction.setAmount(updatedTransaction.getAmount());
        transaction.setCategory(updatedTransaction.getCategory());
        transaction.setDescription(updatedTransaction.getDescription());

        return transactionRepository.save(transaction);
    }


    public String deleteTransaction(String id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found");
        }

        transactionRepository.deleteById(id);
        return "Transaction deleted successfully";
    }

    public List<Transaction> getTransactionsByType(String userId, Transaction.TransactionType type) {
        return transactionRepository.findByUserIdAndType(userId, type);
    }
}