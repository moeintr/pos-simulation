package com.simulation.shaparak.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "bank")
public class Bank {
    @Id
    private ObjectId id;
    private String bankName;
    private String transactionUrl;
    @Indexed(unique = true)
    private String firstSixCardNumber;
    private String signInUrl;
    private String signUpUrl;
    private BankUser bankUser;
    private String token;
    private String receiveBalanceUrl;
}
