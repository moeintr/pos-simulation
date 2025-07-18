package com.simulation.bank.karafarin.core.exception_handling;

import com.simulation.bank.karafarin.core.model.ResponseStructure;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ErrorResponseEntity extends ResponseStructure {
}
