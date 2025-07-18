package com.simulation.psp.usecase.add_pos_usecase;

import com.simulation.psp.core.model.Pos;
import com.simulation.psp.core.repository.PosRepository;
import com.simulation.psp.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddPosUseCase implements UseCase<AddPosUseCaseRequest, AddPosUseCaseResponse> {
    private final PosRepository posRepository;
    @Override
    public AddPosUseCaseResponse execute(AddPosUseCaseRequest request) {
        Pos pos = new Pos()
                .setPosId(UUID.randomUUID().toString())
                .setCreatedAt(LocalDateTime.now())
                .setCardNumber(request.getCardNumber());
        pos = posRepository.save(pos);
        return new AddPosUseCaseResponse(pos.getPosId(), pos.getCreatedAt());
    }
}
