package com.simulation.bank.mellat.usecase.transaction_usecase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/transaction")
public class TransactionUseCaseController {
    private final TransactionUseCase transactionUseCase;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TransactionUseCaseResponse transaction(@Valid @RequestBody TransactionUseCaseRequest request) {
        return transactionUseCase.execute(request);
    }
}
