package com.demo.bankapp.dao;

import com.demo.bankapp.model.entity.AccountEntity;
import com.demo.bankapp.model.entity.ClientEntity;
import com.demo.bankapp.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountDaoImpl implements AccountDao {

    private final AccountRepository accountRepository;

    @Override
    public AccountEntity insertAccount(final AccountEntity accountEntity) {
        return accountRepository.save(accountEntity);
    }

    @Override
    public Optional<AccountEntity> findAccountById(final long accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public void deleteAccount(final long accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public List<AccountEntity> getClientAccounts(ClientEntity clientEntity) {
        return accountRepository.findAccountEntitiesByClientEntity(clientEntity);
    }
}
