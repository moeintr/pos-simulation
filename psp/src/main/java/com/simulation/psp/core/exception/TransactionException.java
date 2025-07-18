package com.simulation.psp.core.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.simulation.psp.core.exception_handling.ErrorResponseEntity;
import com.simulation.psp.core.model.TransactionStatus;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransactionException extends RuntimeException{
    private ErrorResponseEntity errorResponseEntity;
    public TransactionException(Object errorResopneObject) {
        this.errorResponseEntity = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .convertValue(errorResopneObject, ErrorResponseEntity.class);
        this.errorResponseEntity.setStatus(TransactionStatus.FAILED);
    }
}
