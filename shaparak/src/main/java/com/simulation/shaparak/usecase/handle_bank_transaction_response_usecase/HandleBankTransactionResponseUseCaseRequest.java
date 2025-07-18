package com.simulation.shaparak.usecase.handle_bank_transaction_response_usecase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class HandleBankTransactionResponseUseCaseRequest {
    @JsonIgnore
    private Integer pspId;
    private String transactionId;
    private LocalDateTime requestDate;
    private String sourceCardNumber;
    private String destinationCardNumber;
    private String password;
    private BigDecimal price;
}
