package com.simulation.bank.karafarin.usecase.add_account_usecase;


import com.simulation.bank.karafarin.core.configuration.SHA;
import com.simulation.bank.karafarin.core.entity.Account;
import com.simulation.bank.karafarin.core.repository.AccountRepository;
import com.simulation.bank.karafarin.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddAccountUseCase implements UseCase<AddAccountUseCaseRequest, AddAccountUseCaseResponse> {
    private final AccountRepository accountRepository;
    @Override
    public AddAccountUseCaseResponse execute(AddAccountUseCaseRequest request) {
        Account account = new Account()
                .setCardNumber(request.getCardNumber())
                .setBalance(request.getBalance())
                .setPassword(SHA.getHash512(request.getPassword()));
        account = accountRepository.save(account);
        //validate request
        //save to db
        return new AddAccountUseCaseResponse()
                .setAccountId(account.getAccountId());
    }
}
