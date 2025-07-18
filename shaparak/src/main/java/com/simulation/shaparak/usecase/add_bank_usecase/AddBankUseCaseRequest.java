package com.simulation.shaparak.usecase.add_bank_usecase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddBankUseCaseRequest {
    @NotNull
    private String bankName;
    @NotNull
    private String transactionUrl;
    @NotNull
    private String firstSixCardNumber;
    @NotNull
    private String signInUrl;
    @NotNull
    private String signUpUrl;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String receiveBalanceUrl;
    private String token;
}
