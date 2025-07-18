package com.simulation.bank.karafarin.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "transactions")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 36, unique = true, nullable = false)
    private String transactionId;
    private LocalDateTime requestDate;
    private LocalDateTime transactionDate;
    private BigDecimal price;
    @Column(length = 16)
    private String cardNumber;
    @Column(length = 16)
    private String destinationCardNumber;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
