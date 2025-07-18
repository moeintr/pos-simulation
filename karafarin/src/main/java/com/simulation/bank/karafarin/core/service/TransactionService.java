package com.simulation.bank.karafarin.core.service;

import com.simulation.bank.karafarin.core.entity.Transaction;
import com.simulation.bank.karafarin.core.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}

