package com.simulation.shaparak.usecase.handle_receive_balance_usecase;

import com.simulation.shaparak.core.model.*;
import com.simulation.shaparak.core.repository.BankRepository;
import com.simulation.shaparak.core.repository.PspTransactionRepository;
import com.simulation.shaparak.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class HandleReceiveBalanceUseCase implements UseCase<HandleReceiveBalanceUseCaseRequest, HandleReceiveBalanceUseCaseResponse> {
    private final PspTransactionRepository pspTransactionRepository;
    private final RestTemplate restTemplate;
    private final BankRepository bankRepository;
    @Override
    public HandleReceiveBalanceUseCaseResponse execute(HandleReceiveBalanceUseCaseRequest request) {
        String firstSixCardNumber = request.getCardNumber().substring(0, 6);

        Optional<Bank> bank = bankRepository.findByFirstSixCardNumber(firstSixCardNumber);

        HandleReceiveBalanceUseCaseResponse bankResponse = null;
        if (bank.isEmpty()) {
            bankResponse = new HandleReceiveBalanceUseCaseResponse()
                    .setErrorMessage("عدم پشتیبانی از کارت توسط شاپرک")
                    .setStatus(TransactionStatus.FAILED)
                    .setTransactionDate(LocalDateTime.now())
                    .setTransactionId(request.getTransactionId());
        }else {
            HttpStatusCode httpStatusCode;
            do {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(bank.get().getToken());
                HttpEntity<HandleReceiveBalanceUseCaseRequest> entity = new HttpEntity<>(request, headers);
                ResponseEntity<HandleReceiveBalanceUseCaseResponse> response = restTemplate.postForEntity(
                        bank.get().getReceiveBalanceUrl(),
                        entity,
                        HandleReceiveBalanceUseCaseResponse.class
                );
                httpStatusCode = response.getStatusCode();
                if (httpStatusCode == HttpStatus.FORBIDDEN) {
                    ResponseEntity<Token> loginResponse = restTemplate.postForEntity(
                            bank.get().getSignInUrl(),
                            bank.get().getBankUser(),
                            Token.class
                    );
                    bank.get().setToken(loginResponse.getBody().getToken());
                } else {
                    bankResponse = response.getBody();
                }
            } while (httpStatusCode == HttpStatus.FORBIDDEN);
            bankRepository.save(bank.get());
        }

        PspTransaction pspTransaction = new PspTransaction()
                .setPspId(request.getPspId())
                .setTransactionId(request.getTransactionId())
                .setSourceCardNumber(request.getCardNumber())
                .setPrice(bankResponse.getFee())
                .setCreatedAt(LocalDateTime.now())
                .setRequestDate(request.getRequestDate())
                .setStatus(bankResponse.getStatus())
                .setContentType(ContentType.RESPONSE)
                .setTransactionDate(bankResponse.getTransactionDate())
                .setErrorMessage(bankResponse.getErrorMessage());
        pspTransactionRepository.save(pspTransaction);

        log.info("contentType={}, transactionId={}, status={}, transactionDate={}, errorMessage={}",
                pspTransaction.getContentType(), bankResponse.getTransactionId(), bankResponse.getStatus(), bankResponse.getTransactionDate(), bankResponse.getErrorMessage());

        return bankResponse;
    }
}
