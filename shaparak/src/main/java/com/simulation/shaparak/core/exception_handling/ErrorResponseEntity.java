package com.simulation.shaparak.core.exception_handling;

import com.simulation.shaparak.core.model.TransactionStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ErrorResponseEntity {
    private String transactionId;
    private TransactionStatus status;
    private LocalDateTime transactionDate;
    private String errorMessage;
}
