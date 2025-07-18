package com.simulation.bank.karafarin.usecase.auth_usecase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

