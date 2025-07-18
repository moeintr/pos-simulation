package com.simulation.bank.mellat.core.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.simulation.bank.mellat.core.entity.TransactionStatus;
import com.simulation.bank.mellat.core.exception_handling.ErrorResponseEntity;
import com.simulation.bank.mellat.core.model.ResponseStructure;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransactionException extends RuntimeException{
    private ErrorResponseEntity errorResponseEntity;
    public TransactionException(ResponseStructure errorResponseObject) {
        this.errorResponseEntity = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .convertValue(errorResponseObject, ErrorResponseEntity.class);
        this.errorResponseEntity.setStatus(TransactionStatus.FAILED);
    }
}
