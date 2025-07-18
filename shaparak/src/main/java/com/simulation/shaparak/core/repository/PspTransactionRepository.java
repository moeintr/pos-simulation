package com.simulation.shaparak.core.repository;

import com.simulation.shaparak.core.model.PspTransaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PspTransactionRepository extends MongoRepository<PspTransaction, ObjectId> {
}
