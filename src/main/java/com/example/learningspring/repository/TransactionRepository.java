package com.example.learningspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.learningspring.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
}
