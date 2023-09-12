package com.example.learningspring.service;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.learningspring.dto.CreateTransactionDto;
import com.example.learningspring.exception.AppException;
import com.example.learningspring.model.Transaction;
import com.example.learningspring.model.User;
import com.example.learningspring.repository.TransactionRepository;
import com.example.learningspring.repository.UserRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction createTransaction(final CreateTransactionDto transactionData) {
        User foundPayer = userRepository.findById(transactionData.getPayer_id())
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        if (Objects.equals(foundPayer.getType(), "SELLER")) {
            throw new AppException("SELLER type cannot send money", HttpStatus.NOT_FOUND);
        }

        User foundPayee = userRepository.findById(transactionData.getPayee_id())
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        final float transactionValue = transactionData.getValue();

        final float payerCurrentBalance = foundPayer.getBalance();

        if (payerCurrentBalance < transactionValue) {
            throw new AppException("Payer balance not sufficient", HttpStatus.FORBIDDEN);
        }

        final float payeeCurrentBalance = foundPayee.getBalance();

        foundPayer.setBalance(payerCurrentBalance - transactionValue);
        foundPayee.setBalance(payeeCurrentBalance + transactionValue);

        final Transaction createTransaction = new Transaction(foundPayer, foundPayee, transactionValue);

        return transactionRepository.save(createTransaction);
    }

    public Transaction retrieveTransaction(final long id) {
        final Transaction foundTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new AppException("Transaction not found!", HttpStatus.NOT_FOUND));

        return foundTransaction;
    }
}
