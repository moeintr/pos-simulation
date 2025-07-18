package com.simulation.bank.mellat.usecase.auth_usecase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthUseCaseController {
    private final AuthUseCase authUseCase;

    @PostMapping("/signin")
    public AuthUseCaseResponse login(@Valid @RequestBody AuthUseCaseRequest request) {
        return authUseCase.execute(request);
    }


    @PostMapping("/signup")
    public AuthUseCaseResponse signupUser(@Valid @RequestBody AuthUseCaseRequest request) {
        return authUseCase.signupUser(request);
    }
}

