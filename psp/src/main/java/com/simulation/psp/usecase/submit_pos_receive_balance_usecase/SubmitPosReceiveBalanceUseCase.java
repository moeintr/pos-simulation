package com.simulation.psp.usecase.submit_pos_receive_balance_usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simulation.psp.core.configuration.RabbitMQConfig;
import com.simulation.psp.core.model.ContentType;
import com.simulation.psp.core.model.PosTransaction;
import com.simulation.psp.core.model.TransactionStatus;
import com.simulation.psp.core.repository.PosTransactionRepository;
import com.simulation.psp.usecase.UseCase;
import com.simulation.psp.usecase.submit_pos_transaction_usecase.SubmitPosTransactionUseCaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubmitPosReceiveBalanceUseCase implements UseCase<SubmitPosReceiveBalanceUseCaseRequest, SubmitPosReceiveBalanceUseCaseResponse> {
    private final PosTransactionRepository posTransactionRepository;
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;
    @Override
    public SubmitPosReceiveBalanceUseCaseResponse execute(SubmitPosReceiveBalanceUseCaseRequest request) {
        Object shaparakResponse = amqpTemplate.convertSendAndReceive(
                RabbitMQConfig.EXCHANGE
                ,RabbitMQConfig.BALANCE_ROUTING_KEY
                ,request);

        PosTransaction posTransaction = new PosTransaction()
                .setPosId(request.getPosId())
                .setTransactionId(request.getTransactionId())
                .setCreatedAt(LocalDateTime.now())
                .setContentType(ContentType.RESPONSE)
                .setSourceCardNumber(request.getCardNumber())
                .setTransactionDate(LocalDateTime.now())
                .setRequestDate(request.getRequestDate());

        SubmitPosReceiveBalanceUseCaseResponse response;
        if (shaparakResponse == null) {
            response = new SubmitPosReceiveBalanceUseCaseResponse()
                    .setErrorMessage("عدم دریافت پاسخ تراکنش از شبکه پرداخت (شاپرک)")
                    .setStatus(TransactionStatus.FAILED)
                    .setTransactionId(posTransaction.getTransactionId())
                    .setTransactionDate(posTransaction.getTransactionDate());
        } else {
            response = objectMapper.convertValue(shaparakResponse, SubmitPosReceiveBalanceUseCaseResponse.class);
            posTransaction
                    .setStatus(response.getStatus())
                    .setErrorMessage(response.getErrorMessage())
                    .setTransactionDate(response.getTransactionDate());
        }

        log.info("contentType={}, transactionId={}, status={}, transactionDate={}, errorMessage={}",
                ContentType.RESPONSE, response.getTransactionId(), response.getStatus(), posTransaction.getTransactionDate(), response.getErrorMessage());

        posTransactionRepository.save(posTransaction);

        return response;
    }
}
