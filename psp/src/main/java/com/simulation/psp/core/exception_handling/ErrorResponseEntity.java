package com.simulation.psp.core.exception_handling;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.simulation.psp.core.model.TransactionStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponseEntity {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String transactionId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TransactionStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime transactionDate;
    private String errorMessage;
}
