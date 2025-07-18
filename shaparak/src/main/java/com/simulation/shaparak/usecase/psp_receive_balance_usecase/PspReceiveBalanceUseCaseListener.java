package com.simulation.shaparak.usecase.psp_receive_balance_usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PspReceiveBalanceUseCaseListener {
    private final PspReceiveBalanceUseCase pspReceiveBalanceUseCase;

    @RabbitListener(queues = "balance.transaction.queue")
    public Object listenPspReceiveBalance(PspReceiveBalanceUseCaseRequest request) {
        return pspReceiveBalanceUseCase.execute(request);
    }
}
