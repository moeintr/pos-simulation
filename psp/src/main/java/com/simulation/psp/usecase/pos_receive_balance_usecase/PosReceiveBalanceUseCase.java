package com.simulation.psp.usecase.pos_receive_balance_usecase;

import com.simulation.psp.core.configuration.RSA;
import com.simulation.psp.core.exception.NotFoundPosException;
import com.simulation.psp.core.exception.ShaparakResponseException;
import com.simulation.psp.core.exception_handling.ErrorResponseEntity;
import com.simulation.psp.core.model.ContentType;
import com.simulation.psp.core.model.Pos;
import com.simulation.psp.core.model.PosTransaction;
import com.simulation.psp.core.model.TransactionStatus;
import com.simulation.psp.core.repository.PosRepository;
import com.simulation.psp.core.repository.PosTransactionRepository;
import com.simulation.psp.usecase.UseCase;
import com.simulation.psp.usecase.submit_pos_receive_balance_usecase.SubmitPosReceiveBalanceUseCase;
import com.simulation.psp.usecase.submit_pos_receive_balance_usecase.SubmitPosReceiveBalanceUseCaseRequest;
import com.simulation.psp.usecase.submit_pos_receive_balance_usecase.SubmitPosReceiveBalanceUseCaseResponse;
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
public class PosReceiveBalanceUseCase implements UseCase<PosReceiveBalanceUseCaseRequest, PosReceiveBalanceUseCaseResponse> {
    @Value("${psp.id}")
    private Integer pspId;
    @Value("${rsa.key.password.public}")
    private String publicKey;
    private final PosTransactionRepository posTransactionRepository;
    private final SubmitPosReceiveBalanceUseCase submitPosReceiveBalanceUseCase;
    private final PosRepository posRepository;
    @SneakyThrows
    public PosReceiveBalanceUseCaseResponse execute(PosReceiveBalanceUseCaseRequest request) {
        if (request.getTransactionId() == null) {
            request.setTransactionId(UUID.randomUUID().toString());
        }
        posRepository.findByPosId(request.getPosId())
                .orElseThrow(() -> new NotFoundPosException(
                        new ErrorResponseEntity()
                                .setTransactionId(request.getTransactionId())
                                .setTransactionDate(LocalDateTime.now())
                ));
        //validate request
        //handle request -> save pos transaction with pending status and log in mongo
        PosTransaction posTransaction = new PosTransaction()
                .setPosId(request.getPosId())
                .setContentType(ContentType.REQUEST)
                .setTransactionId(request.getTransactionId())
                .setSourceCardNumber(request.getCardNumber())
                .setRequestDate(LocalDateTime.now())
                .setCreatedAt(LocalDateTime.now())
                .setStatus(TransactionStatus.PENDING);
        posTransaction = posTransactionRepository.save(posTransaction);

        log.info("contentType={}, transactionId={}, posId={}, status={}, requestDate={}",
                posTransaction.getContentType(), posTransaction.getTransactionId(), posTransaction.getPosId(), posTransaction.getStatus(), posTransaction.getRequestDate());

        //handle shaparak response in new transaction -> update pos transaction status and save new log
        SubmitPosReceiveBalanceUseCaseRequest shaparakRequest = new SubmitPosReceiveBalanceUseCaseRequest()
                .setPosId(request.getPosId())
                .setRequestDate(posTransaction.getRequestDate())
                .setPassword(RSA.getRSAEncrypt(publicKey, request.getPassword()))
                .setTransactionId(posTransaction.getTransactionId())
                .setPspId(pspId)
                .setCardNumber(request.getCardNumber());
        SubmitPosReceiveBalanceUseCaseResponse shaparakResponse = submitPosReceiveBalanceUseCase.execute(shaparakRequest);

        if (shaparakResponse.getStatus() == TransactionStatus.FAILED) {
            throw new ShaparakResponseException(shaparakResponse);
        }
        //return response
        return new PosReceiveBalanceUseCaseResponse()
                .setStatus(shaparakResponse.getStatus())
                .setTransactionDate(shaparakResponse.getTransactionDate())
                .setTransactionId(shaparakResponse.getTransactionId())
                .setErrorMessage(shaparakResponse.getErrorMessage())
                .setBalance(shaparakResponse.getBalance().toPlainString())
                .setFee(shaparakResponse.getFee());
    }
}
