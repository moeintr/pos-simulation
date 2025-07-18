package com.simulation.bank.mellat.usecase.add_account_usecase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/add-account")
public class AddAccountUseCaseController {
    private final AddAccountUseCase addAccountUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AddAccountUseCaseResponse addAccount(@Valid @RequestBody AddAccountUseCaseRequest request) {
        return addAccountUseCase.execute(request);
    }
}
