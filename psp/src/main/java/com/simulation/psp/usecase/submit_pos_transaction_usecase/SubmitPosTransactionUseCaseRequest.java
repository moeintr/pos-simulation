package com.simulation.psp.usecase.submit_pos_transaction_usecase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simulation.psp.core.model.TransactionStatus;
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
public class SubmitPosTransactionUseCaseRequest {
    private Integer pspId;
    private String transactionId;
    private LocalDateTime requestDate;
    private String sourceCardNumber;
    private String destinationCardNumber;
    private String password;
    private BigDecimal price;
    private TransactionStatus status;
    @JsonIgnore
    private String posId;
}
