package com.simulation.psp.usecase.pos_transaction_usecase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pos-transaction")
public class PosTransactionController {
    private final PosTransactionUseCase useCase;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public PosTransactionUseCaseResponse posTransaction(@Valid @RequestBody PosTransactionUseCaseRequest request) {
        return useCase.execute(request);
    }
}
