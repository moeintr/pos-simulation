package com.simulation.bank.mellat.core.exception_handling;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simulation.bank.mellat.core.model.ResponseStructure;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponseEntity extends ResponseStructure {
}
