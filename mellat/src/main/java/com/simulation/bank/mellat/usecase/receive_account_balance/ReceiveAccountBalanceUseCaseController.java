package com.simulation.bank.mellat.usecase.receive_account_balance;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/receive-balance")
public class ReceiveAccountBalanceUseCaseController {
    private final ReceiveAccountBalanceUseCase useCase;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ReceiveAccountBalanceUseCaseResponse receiveBalance(@Valid @RequestBody ReceiveAccountBalanceUseCaseRequest request) {
        return useCase.execute(request);
    }
}
