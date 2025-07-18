package com.simulation.shaparak.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "psp_transaction")
public class PspTransaction {
    @Id
    private ObjectId id;
    private Integer pspId;
    private ContentType contentType;
    private String transactionId;
    private String sourceCardNumber;
    private String destinationCardNumber;
    private BigDecimal price;
    private TransactionStatus status;
    private LocalDateTime requestDate;
    private LocalDateTime transactionDate;
    private LocalDateTime createdAt;
    private String errorMessage;
}
