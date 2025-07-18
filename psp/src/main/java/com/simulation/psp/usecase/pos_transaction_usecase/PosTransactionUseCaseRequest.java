package com.simulation.psp.usecase.pos_transaction_usecase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PosTransactionUseCaseRequest {
    @Length(min = 36, max = 36, message = "شناسه پذیرنده (پوز) باید دارای 36 کارکتر باشد")
    @NotNull(message = "شناسه پذیرنده (پوز) الزامی است")
    private String posId;
    private String transactionId;
    @Length(min = 16, max = 16, message = "شماره کارت مقصد باید دارای 16 رقم باشد")
    @NotNull(message = "شمار کارت مقصد الزامی است")
    private String destinationCardNumber;
    @NotNull(message = "رمز عبور الزامی است")
    private String password;
    @DecimalMin(value = "1000.0", inclusive = false, message = "مبلغ تراکنش باید بیشتر 1000 تومان باشد")
    @NotNull(message = "مبلغ تراکنش الزامی است")
    private BigDecimal price;

    public PosTransactionUseCaseRequest setTransactionId(String transactionId) {
        this.transactionId = transactionId != null ? transactionId : UUID.randomUUID().toString();
        return this;
    }
}
