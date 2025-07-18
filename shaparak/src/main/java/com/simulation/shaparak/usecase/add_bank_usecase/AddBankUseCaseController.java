package com.simulation.shaparak.usecase.add_bank_usecase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/add-bank")
@Slf4j
public class AddBankUseCaseController {
    private final AddBankUseCase addBankUseCase;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AddBankUseCaseResponse addBank(@Valid @RequestBody AddBankUseCaseRequest request) {
        return addBankUseCase.execute(request);
    }
}
