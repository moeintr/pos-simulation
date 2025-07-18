package com.simulation.shaparak.usecase.psp_transaction_usecase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simulation.shaparak.core.model.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PspTransactionUseCaseResponse {
    private String transactionId;
    private TransactionStatus status;
    private LocalDateTime transactionDate;
    private String errorMessage;
}
