package com.simulation.psp.core.repository;

import com.simulation.psp.core.model.Pos;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PosRepository extends MongoRepository<Pos, ObjectId> {
    Optional<Pos> findByPosId(String posId);
    Optional<Pos> findByCardNumber(String cardNumber);
}
