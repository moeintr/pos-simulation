package com.simulation.psp.usecase.add_pos_usecase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddPosUseCaseRequest {
    @Length(min = 16, max = 16, message = "destination card number should have 16 characters")
    private String cardNumber;
}
