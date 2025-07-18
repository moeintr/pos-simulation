package com.simulation.shaparak.usecase.handle_receive_balance_usecase;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class HandleReceiveBalanceUseCaseRequest {
    @JsonIgnore
    private Integer pspId;
    private String transactionId;
    private LocalDateTime requestDate;
    private String cardNumber;
    private String password;
}
