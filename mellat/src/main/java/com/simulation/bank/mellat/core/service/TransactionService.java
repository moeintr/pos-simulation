package com.simulation.bank.mellat.core.service;

import com.simulation.bank.mellat.core.entity.Transaction;
import com.simulation.bank.mellat.core.repository.TransactionRepository;
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

