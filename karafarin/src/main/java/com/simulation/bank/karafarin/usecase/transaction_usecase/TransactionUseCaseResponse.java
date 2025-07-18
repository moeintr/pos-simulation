package com.simulation.bank.karafarin.usecase.transaction_usecase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simulation.bank.karafarin.core.model.ResponseStructure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionUseCaseResponse extends ResponseStructure {
}
