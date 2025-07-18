package com.simulation.psp.usecase.pos_transaction_usecase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.simulation.psp.core.model.TransactionStatus;
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
public class PosTransactionUseCaseResponse {
    private String transactionId;
    private TransactionStatus status;
    private LocalDateTime transactionDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;
}
