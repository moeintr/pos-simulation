package com.simulation.bank.karafarin.usecase.receive_account_balance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class ReceiveAccountBalanceUseCaseRequest {
    private String transactionId;
    private LocalDateTime requestDate;
    private String cardNumber;
    private String password;
}
