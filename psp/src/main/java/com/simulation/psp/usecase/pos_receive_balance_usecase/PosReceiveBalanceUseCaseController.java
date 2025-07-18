package com.simulation.psp.usecase.pos_receive_balance_usecase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/receive-balance")
public class PosReceiveBalanceUseCaseController {
    private final PosReceiveBalanceUseCase useCase;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public PosReceiveBalanceUseCaseResponse receiveBalance(@Valid @RequestBody PosReceiveBalanceUseCaseRequest request) {
        return useCase.execute(request);
    }
}
