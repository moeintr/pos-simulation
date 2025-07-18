package com.simulation.bank.mellat.usecase.transaction_usecase;

import com.simulation.bank.mellat.core.exception.TransactionException;
import com.simulation.bank.mellat.core.service.TransactionService;
import com.simulation.bank.mellat.core.configuration.RSA;
import com.simulation.bank.mellat.core.configuration.SHA;
import com.simulation.bank.mellat.core.entity.Account;
import com.simulation.bank.mellat.core.entity.Transaction;
import com.simulation.bank.mellat.core.entity.TransactionStatus;
import com.simulation.bank.mellat.core.exception.NotEnoughBalanceException;
import com.simulation.bank.mellat.core.exception.NotFoundAccountException;
import com.simulation.bank.mellat.core.exception.NotMatchPasswordException;
import com.simulation.bank.mellat.core.repository.AccountRepository;
import com.simulation.bank.mellat.usecase.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionUseCase implements UseCase<TransactionUseCaseRequest, TransactionUseCaseResponse> {
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    @Value("${rsa.key.password.private}")
    private String privateKey;
    @Transactional
    public TransactionUseCaseResponse execute(TransactionUseCaseRequest request) {
        Transaction transaction = new Transaction()
                .setTransactionId(request.getTransactionId())
                .setTransactionDate(LocalDateTime.now())
                .setRequestDate(request.getRequestDate())
                .setPrice(request.getPrice())
                .setStatus(TransactionStatus.SUCCESSFUL)
                .setCardNumber(request.getSourceCardNumber())
                .setDestinationCardNumber(request.getDestinationCardNumber());

        TransactionUseCaseResponse response = new TransactionUseCaseResponse();
        response.setTransactionId(request.getTransactionId())
                .setTransactionDate(transaction.getTransactionDate())
                .setStatus(transaction.getStatus());
        try {
            //check valid request from shaparak
            Account foundAccount = accountRepository.findByCardNumber(request.getSourceCardNumber())
                    .orElseThrow(() -> new NotFoundAccountException(response));
            //find account by cardNumber and lock it
            String decryptedPassword = RSA.getRSADecrypt(privateKey, request.getPassword());
            String hashedPassword = SHA.getHash512(decryptedPassword);
            if (!foundAccount.getPassword().equals(hashedPassword)) {
                throw new NotMatchPasswordException(response);
            }

            if (foundAccount.getBalance().compareTo(request.getPrice()) < 0) {
                throw new NotEnoughBalanceException(response);
            }
            //check account have enough balance
            foundAccount.setBalance(
                    foundAccount.getBalance().subtract(request.getPrice())
            );
            transactionService.saveTransaction(transaction);
            //two save
            //reduce balance
            //save transaction
            //return response
            return response;
        } catch (Exception e) {
            if (!(e instanceof NotFoundAccountException)) {
                transaction.setStatus(TransactionStatus.FAILED);
                transactionService.saveTransaction(transaction);
            }
            if (!(e instanceof TransactionException)) {
                throw new TransactionException(response);
            }
            throw e;
        }
    }
}
