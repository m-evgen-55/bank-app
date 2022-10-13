package com.demo.bankapp.service;

import com.demo.bankapp.dao.AccountDao;
import com.demo.bankapp.dao.ClientDao;
import com.demo.bankapp.exception.account.AccountException;
import com.demo.bankapp.exception.account.AccountNotFoundException;
import com.demo.bankapp.exception.account.NegativePutSumException;
import com.demo.bankapp.exception.account.NotEnoughMoneyException;
import com.demo.bankapp.exception.client.ClientNotFoundException;
import com.demo.bankapp.model.Account;
import com.demo.bankapp.model.Client;
import com.demo.bankapp.model.entity.AccountEntity;
import com.demo.bankapp.model.entity.ClientEntity;
import com.demo.bankapp.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.demo.bankapp.utils.AccountUtils.mapAccountEntityToAccount;
import static com.demo.bankapp.utils.AccountUtils.mapAccountToAccountEntity;
import static com.demo.bankapp.utils.ClientUtils.mapClientEntityToClient;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;
    private final ClientDao clientDao;

    @Override
    public Account addNewAccount(final long clientId, final Account account) throws ClientNotFoundException {
        final ClientEntity clientEntity = clientDao.getClientById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client id = %s not found", clientId)));

        account.setClientId(clientEntity.getClientId());
        final AccountEntity accountEntity = mapAccountToAccountEntity(account, clientEntity);
        return mapAccountEntityToAccount(accountDao.insertAccount(accountEntity));
    }

    @Override
    public Account putMoney(final long accountId, final BigDecimal putSum) throws AccountException {
        final AccountEntity accountEntity = accountDao.findAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account id = %s not found", accountId)));

        if (putSum.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new NegativePutSumException("The deposited amount is negative. Please enter a positive number.");
        }

        accountEntity.setBalance(accountEntity.getBalance().add(putSum));
        return mapAccountEntityToAccount(accountDao.insertAccount(accountEntity));
    }

    @Override
    public Account getMoney(final long accountId, final BigDecimal getSum) throws AccountException {
        final AccountEntity accountEntity = accountDao.findAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account id = %s not found", accountId)));

        if (getSum.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new NegativePutSumException("The deposited amount is negative. Please enter a positive number.");
        }

        if ((accountEntity.getBalance().subtract(getSum)).compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new NotEnoughMoneyException("Not enough money in the account.");
        }

        accountEntity.setBalance(accountEntity.getBalance().subtract(getSum));

        return mapAccountEntityToAccount(accountDao.insertAccount(accountEntity));
    }

    @Override
    public Account findAccountById(final long accountId) throws AccountNotFoundException {
        final AccountEntity accountEntity = accountDao.findAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account id = %s not found", accountId)));

        return mapAccountEntityToAccount(accountEntity);
    }

    @Override
    public List<Account> getAllClientAccounts(final long clientId) throws ClientNotFoundException {
        final ClientEntity clientEntity = clientDao.getClientById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client id = %s not found", clientId)));

        return accountDao.getClientAccounts(clientEntity)
                .stream()
                .map(AccountUtils::mapAccountEntityToAccount)
                .collect(Collectors.toList());
    }

    @Override
    public Client getClientByAccountId(final long accountId) throws AccountNotFoundException, ClientNotFoundException {
        final AccountEntity accountEntity = accountDao.findAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account id = %s not found", accountId)));

        final ClientEntity clientEntity = clientDao.getClientById(accountEntity.getClientEntity().getClientId()).orElse(null);

        if (clientEntity == null) {
            throw new ClientNotFoundException(String.format("Client id = %s not found", accountEntity.getClientEntity().getClientId()));
        }

        return mapClientEntityToClient(clientEntity);
    }

    @Override
    public void deleteAccount(final long accountId) throws AccountNotFoundException {
        accountDao.findAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account id = %s not found", accountId)));

        accountDao.deleteAccount(accountId);
    }
}
