package com.demo.bankapp.service;

import com.demo.bankapp.exception.account.AccountException;
import com.demo.bankapp.exception.client.ClientException;
import com.demo.bankapp.exception.client.ClientNotFoundException;
import com.demo.bankapp.model.Account;
import com.demo.bankapp.model.Client;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    Account addNewAccount(long clientId, Account account) throws ClientException;

    Account putMoney(long accountId, BigDecimal putSum) throws AccountException;

    Account getMoney(long accountId, BigDecimal getSum) throws AccountException;

    Account findAccountById(long accountId) throws AccountException;

    List<Account> getAllClientAccounts(long clientId) throws ClientNotFoundException;

    Client getClientByAccountId(long accountId) throws AccountException, ClientNotFoundException;

    void deleteAccount(long accountId) throws AccountException;
}
