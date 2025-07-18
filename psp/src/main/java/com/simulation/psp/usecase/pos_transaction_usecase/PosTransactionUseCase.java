package com.simulation.psp.usecase.pos_transaction_usecase;

import com.simulation.psp.core.configuration.RSA;
import com.simulation.psp.core.exception.NotFoundPosException;
import com.simulation.psp.core.exception.ShaparakResponseException;
import com.simulation.psp.core.model.*;
import com.simulation.psp.core.repository.PosRepository;
import com.simulation.psp.core.repository.PosTransactionRepository;
import com.simulation.psp.usecase.UseCase;
import com.simulation.psp.usecase.submit_pos_transaction_usecase.SubmitPosTransactionUseCase;
import com.simulation.psp.usecase.submit_pos_transaction_usecase.SubmitPosTransactionUseCaseRequest;
import com.simulation.psp.usecase.submit_pos_transaction_usecase.SubmitPosTransactionUseCaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PosTransactionUseCase implements UseCase<PosTransactionUseCaseRequest, PosTransactionUseCaseResponse> {
    @Value("${psp.id}")
    private Integer pspId;
    @Value("${rsa.key.password.public}")
    private String publicKey;
    private final PosTransactionRepository posTransactionRepository;
    private final SubmitPosTransactionUseCase submitPosTransactionUseCase;
    private final PosRepository posRepository;

    @SneakyThrows
    public PosTransactionUseCaseResponse execute(PosTransactionUseCaseRequest request) {
        if (request.getTransactionId() == null) {
            request.setTransactionId(UUID.randomUUID().toString());
        }
        Pos pos = posRepository.findByPosId(request.getPosId())
                .orElseThrow(() -> new NotFoundPosException(
                        new PosTransactionUseCaseResponse()
                                .setTransactionId(request.getTransactionId())
                                .setTransactionDate(LocalDateTime.now())
                ));
        //validate request
        //handle request -> save pos transaction with pending status and log in mongo
        PosTransaction posTransaction = new PosTransaction()
                .setPosId(request.getPosId())
                .setContentType(ContentType.REQUEST)
                .setTransactionId(request.getTransactionId())
                .setSourceCardNumber(pos.getCardNumber())
                .setDestinationCardNumber(request.getDestinationCardNumber())
                .setRequestDate(LocalDateTime.now())
                .setCreatedAt(LocalDateTime.now())
                .setStatus(TransactionStatus.PENDING)
                .setPrice(request.getPrice());
        posTransaction = posTransactionRepository.save(posTransaction);

        log.info("contentType={}, transactionId={}, posId={}, status={}, price={}, requestDate={}",
                posTransaction.getContentType(), posTransaction.getTransactionId(), posTransaction.getPosId(), posTransaction.getStatus(), posTransaction.getPrice(), posTransaction.getRequestDate());

        //handle shaparak response in new transaction -> update pos transaction status and save new log
        SubmitPosTransactionUseCaseRequest shaparakRequest = new SubmitPosTransactionUseCaseRequest()
                .setPosId(request.getPosId())
                .setRequestDate(posTransaction.getRequestDate())
                .setPassword(RSA.getRSAEncrypt(publicKey, request.getPassword()))
                .setPrice(request.getPrice())
                .setTransactionId(posTransaction.getTransactionId())
                .setPspId(pspId)
                .setStatus(posTransaction.getStatus())
                .setSourceCardNumber(pos.getCardNumber())
                .setDestinationCardNumber(request.getDestinationCardNumber());
        SubmitPosTransactionUseCaseResponse shaparakResponse = submitPosTransactionUseCase.execute(shaparakRequest);

        if (shaparakResponse.getStatus() == TransactionStatus.FAILED) {
            throw new ShaparakResponseException(shaparakResponse);
        }
        //return response
        return new PosTransactionUseCaseResponse()
                .setStatus(shaparakResponse.getStatus())
                .setTransactionDate(shaparakResponse.getTransactionDate())
                .setTransactionId(shaparakResponse.getTransactionId())
                .setErrorMessage(shaparakResponse.getErrorMessage());
    }
}
