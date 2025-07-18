package com.simulation.bank.mellat.usecase.add_account_usecase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddAccountUseCaseRequest {
    @Length(min = 16, max = 16, message = "card number should have 16 characters")
    private String cardNumber;
    private BigDecimal balance;
    private String password;
}
