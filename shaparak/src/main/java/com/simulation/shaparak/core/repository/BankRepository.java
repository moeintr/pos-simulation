package com.simulation.shaparak.core.repository;

import com.simulation.shaparak.core.model.Bank;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BankRepository extends MongoRepository<Bank, ObjectId> {
    Optional<Bank> findByFirstSixCardNumber(String firstSixCardNumber);
}
