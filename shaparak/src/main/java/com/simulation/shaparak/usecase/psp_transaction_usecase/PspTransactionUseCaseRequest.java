package com.simulation.shaparak.usecase.psp_transaction_usecase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simulation.shaparak.core.model.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PspTransactionUseCaseRequest {
    private Integer pspId;
    private String transactionId;
    private LocalDateTime requestDate;
    private String sourceCardNumber;
    private String destinationCardNumber;
    private String password;
    private BigDecimal price;
    private TransactionStatus status;
}
