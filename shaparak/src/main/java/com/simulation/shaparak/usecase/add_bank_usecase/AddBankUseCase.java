package com.simulation.shaparak.usecase.add_bank_usecase;

import com.simulation.shaparak.core.model.Bank;
import com.simulation.shaparak.core.model.BankUser;
import com.simulation.shaparak.core.repository.BankRepository;
import com.simulation.shaparak.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddBankUseCase implements UseCase<AddBankUseCaseRequest, AddBankUseCaseResponse> {
    private final BankRepository bankRepository;

    @SneakyThrows
    public AddBankUseCaseResponse execute(AddBankUseCaseRequest request) {
        bankRepository.save(new Bank()
                .setBankName(request.getBankName())
                .setTransactionUrl(request.getTransactionUrl())
                .setFirstSixCardNumber(request.getFirstSixCardNumber())
                .setSignInUrl(request.getSignInUrl())
                .setSignUpUrl(request.getSignUpUrl())
                .setToken(request.getToken())
                .setReceiveBalanceUrl(request.getReceiveBalanceUrl())
                .setBankUser(new BankUser(request.getUsername(), request.getPassword())
                ));
        return new AddBankUseCaseResponse();
    }
}
