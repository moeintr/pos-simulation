package com.simulation.psp.usecase.add_pos_usecase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/add-pos")
@RequiredArgsConstructor
@Slf4j
public class AddPosUseCaseController {
    private final AddPosUseCase useCase;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AddPosUseCaseResponse addPos(@Valid @RequestBody AddPosUseCaseRequest request) {
        return useCase.execute(request);
    }
}
