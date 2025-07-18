package com.simulation.bank.karafarin.core.repository;

import com.simulation.bank.karafarin.core.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
