package com.simulation.shaparak.usecase.handle_bank_transaction_response_usecase;

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
public class HandleBankTransactionResponseUseCase implements UseCase<HandleBankTransactionResponseUseCaseRequest, HandleBankTransactionResponseUseCaseResponse> {
    private final PspTransactionRepository pspTransactionRepository;
    private final RestTemplate restTemplate;
    private final BankRepository bankRepository;

    @Override
    public HandleBankTransactionResponseUseCaseResponse execute(HandleBankTransactionResponseUseCaseRequest request) {
        //call shaparak transaction api
        //receive response from shaparak
        //save log
        //return it
        String firstSixCardNumber = request.getSourceCardNumber().substring(0, 6);

        Optional<Bank> bank = bankRepository.findByFirstSixCardNumber(firstSixCardNumber);

        HandleBankTransactionResponseUseCaseResponse bankResponse = null;
        if (bank.isEmpty()) {
            bankResponse = new HandleBankTransactionResponseUseCaseResponse()
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
                HttpEntity<HandleBankTransactionResponseUseCaseRequest> entity = new HttpEntity<>(request, headers);
                ResponseEntity<HandleBankTransactionResponseUseCaseResponse> response = restTemplate.postForEntity(
                        bank.get().getTransactionUrl(),
                        entity,
                        HandleBankTransactionResponseUseCaseResponse.class
                );
                httpStatusCode = response.getStatusCode();
                if (httpStatusCode == HttpStatus.FORBIDDEN) {
                    ResponseEntity<Token> loginResponse = restTemplate.postForEntity(
                            bank.get().getSignInUrl(),
                            bank.get().getBankUser(),
                            Token.class
                    );
                    if (loginResponse.getStatusCode() == HttpStatus.FORBIDDEN) {
                        ResponseEntity<Token> signupResponse = restTemplate.postForEntity(
                                bank.get().getSignUpUrl(),
                                bank.get().getBankUser(),
                                Token.class
                        );
                        bank.get().setToken(signupResponse.getBody().getToken());
                    } else {
                        bank.get().setToken(loginResponse.getBody().getToken());
                    }
                } else {
                    bankResponse = response.getBody();
                }
            } while (httpStatusCode == HttpStatus.FORBIDDEN);
            bankRepository.save(bank.get());
        }


        PspTransaction pspTransaction = new PspTransaction()
                .setPspId(request.getPspId())
                .setTransactionId(request.getTransactionId())
                .setSourceCardNumber(request.getSourceCardNumber())
                .setDestinationCardNumber(request.getDestinationCardNumber())
                .setPrice(request.getPrice())
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
