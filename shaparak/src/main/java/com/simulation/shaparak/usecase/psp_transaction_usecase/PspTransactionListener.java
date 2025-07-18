package com.simulation.shaparak.usecase.psp_transaction_usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PspTransactionListener {
    private final PspTransactionUseCase pspTransactionUseCase;

    @RabbitListener(queues = "payment.transaction.queue")
    public Object listenPspTransaction(PspTransactionUseCaseRequest request) {
        return pspTransactionUseCase.execute(request);
    }
}
