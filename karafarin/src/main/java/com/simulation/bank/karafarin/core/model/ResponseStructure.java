package com.simulation.bank.karafarin.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simulation.bank.karafarin.core.entity.TransactionStatus;
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
public abstract class ResponseStructure {
    private String transactionId;
    private TransactionStatus status;
    private LocalDateTime transactionDate;
    private String errorMessage;
}
