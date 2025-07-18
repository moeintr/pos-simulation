package com.simulation.shaparak.usecase.psp_transaction_usecase;

import com.simulation.shaparak.core.model.ContentType;
import com.simulation.shaparak.core.model.PspTransaction;
import com.simulation.shaparak.core.repository.PspTransactionRepository;
import com.simulation.shaparak.usecase.UseCase;
import com.simulation.shaparak.usecase.handle_bank_transaction_response_usecase.HandleBankTransactionResponseUseCase;
import com.simulation.shaparak.usecase.handle_bank_transaction_response_usecase.HandleBankTransactionResponseUseCaseRequest;
import com.simulation.shaparak.usecase.handle_bank_transaction_response_usecase.HandleBankTransactionResponseUseCaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class PspTransactionUseCase implements UseCase<PspTransactionUseCaseRequest, PspTransactionUseCaseResponse> {
    private final PspTransactionRepository pspTransactionRepository;
    private final HandleBankTransactionResponseUseCase handleBankTransactionResponseUseCase;
    @Override
    public PspTransactionUseCaseResponse execute(PspTransactionUseCaseRequest request) {
        //handle transaction request data from psp app -> save psp transaction status and log in mongo
        PspTransaction pspTransaction = new PspTransaction()
                .setPspId(request.getPspId())
                .setTransactionId(request.getTransactionId())
                .setRequestDate(request.getRequestDate())
                .setPrice(request.getPrice())
                .setStatus(request.getStatus())
                .setCreatedAt(LocalDateTime.now())
                .setSourceCardNumber(request.getSourceCardNumber())
                .setDestinationCardNumber(request.getDestinationCardNumber())
                .setContentType(ContentType.REQUEST);
        pspTransaction = pspTransactionRepository.save(pspTransaction);

        log.info("contentType={}, transactionId={}, pspId={}, status={}, price={}, requestDate={}",
                pspTransaction.getContentType(), pspTransaction.getTransactionId(), pspTransaction.getPspId(), pspTransaction.getStatus(), pspTransaction.getPrice(), pspTransaction.getRequestDate());

        HandleBankTransactionResponseUseCaseRequest bankRequest = new HandleBankTransactionResponseUseCaseRequest()
                .setRequestDate(request.getRequestDate())
                .setTransactionId(request.getTransactionId())
                .setPspId(request.getPspId())
                .setPassword(request.getPassword())
                .setSourceCardNumber(request.getSourceCardNumber())
                .setDestinationCardNumber(request.getDestinationCardNumber())
                .setPrice(request.getPrice());
        HandleBankTransactionResponseUseCaseResponse bankResponse = handleBankTransactionResponseUseCase.execute(bankRequest);
        //receive and update status response from rabbit and return it
        return new PspTransactionUseCaseResponse()
                .setErrorMessage(bankResponse.getErrorMessage())
                .setTransactionId(bankResponse.getTransactionId())
                .setTransactionDate(bankResponse.getTransactionDate())
                .setStatus(bankResponse.getStatus());
    }
}
