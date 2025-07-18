package com.simulation.shaparak.usecase.psp_receive_balance_usecase;

import com.simulation.shaparak.core.model.ContentType;
import com.simulation.shaparak.core.model.PspTransaction;
import com.simulation.shaparak.core.model.TransactionStatus;
import com.simulation.shaparak.core.repository.PspTransactionRepository;
import com.simulation.shaparak.usecase.UseCase;
import com.simulation.shaparak.usecase.handle_receive_balance_usecase.HandleReceiveBalanceUseCase;
import com.simulation.shaparak.usecase.handle_receive_balance_usecase.HandleReceiveBalanceUseCaseRequest;
import com.simulation.shaparak.usecase.handle_receive_balance_usecase.HandleReceiveBalanceUseCaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class PspReceiveBalanceUseCase implements UseCase<PspReceiveBalanceUseCaseRequest, PspReceiveBalanceUseCaseResponse> {
    private final PspTransactionRepository pspTransactionRepository;
    private final HandleReceiveBalanceUseCase handleReceiveBalanceUseCase;
    @Override
    public PspReceiveBalanceUseCaseResponse execute(PspReceiveBalanceUseCaseRequest request) {
        PspTransaction pspTransaction = new PspTransaction()
                .setPspId(request.getPspId())
                .setTransactionId(request.getTransactionId())
                .setRequestDate(request.getRequestDate())
                .setStatus(TransactionStatus.PENDING)
                .setCreatedAt(LocalDateTime.now())
                .setSourceCardNumber(request.getCardNumber())
                .setContentType(ContentType.REQUEST);
        pspTransaction = pspTransactionRepository.save(pspTransaction);

        log.info("contentType={}, transactionId={}, pspId={}, status={}, requestDate={}",
                pspTransaction.getContentType(), pspTransaction.getTransactionId(), pspTransaction.getPspId(), pspTransaction.getStatus(), pspTransaction.getRequestDate());

        HandleReceiveBalanceUseCaseRequest bankRequest = new HandleReceiveBalanceUseCaseRequest()
                .setRequestDate(request.getRequestDate())
                .setTransactionId(request.getTransactionId())
                .setPspId(request.getPspId())
                .setPassword(request.getPassword())
                .setCardNumber(request.getCardNumber());
        HandleReceiveBalanceUseCaseResponse bankResponse = handleReceiveBalanceUseCase.execute(bankRequest);
        //receive and update status response from rabbit and return it
        return new PspReceiveBalanceUseCaseResponse()
                .setErrorMessage(bankResponse.getErrorMessage())
                .setTransactionId(bankResponse.getTransactionId())
                .setTransactionDate(bankResponse.getTransactionDate())
                .setStatus(bankResponse.getStatus())
                .setBalance(bankResponse.getBalance())
                .setFee(bankResponse.getFee());
    }
}
