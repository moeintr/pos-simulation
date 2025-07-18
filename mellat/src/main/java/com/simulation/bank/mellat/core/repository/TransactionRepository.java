package com.simulation.bank.mellat.core.repository;

import com.simulation.bank.mellat.core.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
