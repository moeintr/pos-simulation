package com.simulation.psp.usecase.submit_pos_transaction_usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simulation.psp.core.configuration.RabbitMQConfig;
import com.simulation.psp.core.model.ContentType;
import com.simulation.psp.core.model.PosTransaction;
import com.simulation.psp.core.model.TransactionStatus;
import com.simulation.psp.core.repository.PosTransactionRepository;
import com.simulation.psp.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubmitPosTransactionUseCase implements UseCase<SubmitPosTransactionUseCaseRequest, SubmitPosTransactionUseCaseResponse> {
    private final PosTransactionRepository posTransactionRepository;
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;
    @Override
    public SubmitPosTransactionUseCaseResponse execute(SubmitPosTransactionUseCaseRequest request) {
        Object shaparakResponse = amqpTemplate.convertSendAndReceive(
                RabbitMQConfig.EXCHANGE
                ,RabbitMQConfig.PAYMENT_ROUTING_KEY
                ,request);

        PosTransaction posTransaction = new PosTransaction()
                .setPosId(request.getPosId())
                .setTransactionId(request.getTransactionId())
                .setPrice(request.getPrice())
                .setCreatedAt(LocalDateTime.now())
                .setContentType(ContentType.RESPONSE)
                .setDestinationCardNumber(request.getDestinationCardNumber())
                .setSourceCardNumber(request.getSourceCardNumber())
                .setTransactionDate(LocalDateTime.now())
                .setRequestDate(request.getRequestDate());

        SubmitPosTransactionUseCaseResponse response;
        if (shaparakResponse == null) {
            response = new SubmitPosTransactionUseCaseResponse()
                                .setErrorMessage("عدم دریافت پاسخ تراکنش از شبکه پرداخت (شاپرک)")
                                .setStatus(TransactionStatus.FAILED)
                                .setTransactionId(posTransaction.getTransactionId())
                                .setTransactionDate(posTransaction.getTransactionDate());
        } else {
            response = objectMapper.convertValue(shaparakResponse, SubmitPosTransactionUseCaseResponse.class);
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
