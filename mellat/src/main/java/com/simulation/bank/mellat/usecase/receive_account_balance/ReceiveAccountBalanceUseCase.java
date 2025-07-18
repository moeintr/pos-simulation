package com.simulation.bank.mellat.usecase.receive_account_balance;

import com.simulation.bank.mellat.core.configuration.RSA;
import com.simulation.bank.mellat.core.configuration.SHA;
import com.simulation.bank.mellat.core.entity.Account;
import com.simulation.bank.mellat.core.entity.Transaction;
import com.simulation.bank.mellat.core.entity.TransactionStatus;
import com.simulation.bank.mellat.core.exception.NotFoundAccountException;
import com.simulation.bank.mellat.core.exception.NotMatchPasswordException;
import com.simulation.bank.mellat.core.exception_handling.ErrorResponseEntity;
import com.simulation.bank.mellat.core.repository.AccountRepository;
import com.simulation.bank.mellat.core.repository.TransactionRepository;
import com.simulation.bank.mellat.usecase.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReceiveAccountBalanceUseCase implements UseCase<ReceiveAccountBalanceUseCaseRequest, ReceiveAccountBalanceUseCaseResponse> {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    @Value("${rsa.key.password.private}")
    private String privateKey;
    @Transactional
    public ReceiveAccountBalanceUseCaseResponse execute(ReceiveAccountBalanceUseCaseRequest request) {
        Account foundAccount = accountRepository.findByCardNumber(request.getCardNumber())
                .orElseThrow(() -> new NotFoundAccountException(
                        new ErrorResponseEntity()
                                .setTransactionId(request.getTransactionId())
                                .setTransactionDate(LocalDateTime.now())
                ));
        String decryptedPassword = RSA.getRSADecrypt(privateKey, request.getPassword());
        String hashedPassword = SHA.getHash512(decryptedPassword);
        if (!foundAccount.getPassword().equals(hashedPassword)) {
            throw new NotMatchPasswordException(
                    new ErrorResponseEntity()
                            .setTransactionId(request.getTransactionId())
                            .setTransactionDate(LocalDateTime.now())
            );
        }
        BigDecimal fee = BigDecimal.valueOf(1800);
        foundAccount.setBalance(foundAccount.getBalance().subtract(fee));

        accountRepository.save(foundAccount);

        Transaction transaction = new Transaction()
                .setPrice(fee)
                .setRequestDate(request.getRequestDate())
                .setTransactionId(request.getTransactionId())
                .setStatus(TransactionStatus.SUCCESSFUL)
                .setTransactionDate(LocalDateTime.now())
                .setCardNumber(request.getCardNumber());

        transactionRepository.save(transaction);

        ReceiveAccountBalanceUseCaseResponse response = new ReceiveAccountBalanceUseCaseResponse();
        response.setBalance(foundAccount.getBalance())
                .setFee(fee)
                .setTransactionDate(transaction.getTransactionDate())
                .setTransactionId(request.getTransactionId())
                .setStatus(transaction.getStatus());
        return response;
    }
}
