package com.demo.bankapp.dao;


import com.demo.bankapp.model.entity.AccountEntity;
import com.demo.bankapp.model.entity.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface AccountDao {

    AccountEntity insertAccount(AccountEntity accountEntity);

    Optional<AccountEntity> findAccountById(long accountId);

    void deleteAccount(long accountId);

    List<AccountEntity> getClientAccounts(ClientEntity clientEntity);
}
