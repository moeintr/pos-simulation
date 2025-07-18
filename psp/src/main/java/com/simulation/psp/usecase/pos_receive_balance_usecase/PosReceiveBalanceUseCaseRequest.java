package com.simulation.psp.usecase.pos_receive_balance_usecase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PosReceiveBalanceUseCaseRequest {
    @Length(min = 36, max = 36, message = "شناسه پذیرنده (پوز) باید دارای 36 کارکتر باشد")
    @NotNull(message = "شناسه پذیرنده (پوز) الزامی است")
    private String posId;
    private String transactionId;
    @Length(min = 16, max = 16, message = "شماره کارت باید دارای 16 رقم باشد")
    @NotNull(message = "شمار کارت الزامی است")
    private String cardNumber;
    @NotNull(message = "رمز عبور الزامی است")
    private String password;
}
