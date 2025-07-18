package com.simulation.bank.karafarin.usecase.receive_account_balance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simulation.bank.karafarin.core.model.ResponseStructure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiveAccountBalanceUseCaseResponse extends ResponseStructure {
    private BigDecimal balance;
    private BigDecimal fee;
}
