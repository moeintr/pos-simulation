package com.simulation.psp.core.repository;

import com.simulation.psp.core.model.PosTransaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PosTransactionRepository extends MongoRepository<PosTransaction, ObjectId> {
    Optional<PosTransaction> findByTransactionId(String transactionId);
}
