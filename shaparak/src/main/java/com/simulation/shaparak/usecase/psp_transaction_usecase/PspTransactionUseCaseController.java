package com.simulation.shaparak.usecase.psp_transaction_usecase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/psp-transaction")
@RequiredArgsConstructor
public class PspTransactionUseCaseController {
    private final PspTransactionUseCase useCase;
    @PostMapping
    public PspTransactionUseCaseResponse pspTransaction(@Valid @RequestBody PspTransactionUseCaseRequest request) {
        return useCase.execute(request);
    }
}
