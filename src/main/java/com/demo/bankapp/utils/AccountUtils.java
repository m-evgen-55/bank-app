package com.demo.bankapp.utils;

import com.demo.bankapp.model.Account;
import com.demo.bankapp.model.entity.AccountEntity;
import com.demo.bankapp.model.entity.ClientEntity;

public final class AccountUtils {

    private AccountUtils() {
    }

    public static Account mapAccountEntityToAccount(final AccountEntity accountEntity) {
        return new Account()
                .setId(accountEntity.getId())
                .setBalance(accountEntity.getBalance())
                .setClientId(accountEntity.getClientEntity().getClientId());
    }

    public static AccountEntity mapAccountToAccountEntity(final Account account, final ClientEntity clientEntity) {
        return new AccountEntity()
                .setId(account.getId())
                .setBalance(account.getBalance())
                .setClientEntity(clientEntity);
    }
}
