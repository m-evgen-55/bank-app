package com.demo.bankapp.service;

import com.demo.bankapp.dao.AccountDao;
import com.demo.bankapp.dao.ClientDao;
import com.demo.bankapp.exception.account.AccountException;
import com.demo.bankapp.exception.account.NegativePutSumException;
import com.demo.bankapp.exception.account.NotEnoughMoneyException;
import com.demo.bankapp.exception.client.ClientException;
import com.demo.bankapp.exception.client.ClientNotFoundException;
import com.demo.bankapp.model.Account;
import com.demo.bankapp.model.Client;
import com.demo.bankapp.model.entity.AccountEntity;
import com.demo.bankapp.model.entity.ClientEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.demo.bankapp.test_data.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    private AccountDao accountDao;
    private ClientDao clientDao;
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        accountDao = mock(AccountDao.class);
        clientDao = mock(ClientDao.class);
        accountService = new AccountServiceImpl(accountDao, clientDao);
    }

    @Test
    void addNewAccountTest() throws ClientException {
        when(clientDao.getClientById(anyLong())).thenReturn(Optional.of(getClientEntity()));
        final AccountEntity accountEntity = getAccountEntity();
        when(accountDao.insertAccount(any())).thenReturn(accountEntity);

        final Account account = accountService.addNewAccount(1, getAccount());

        assertThat(account).isNotNull();
        Assertions.assertEquals(accountEntity.getId(), account.getId());
        Assertions.assertEquals(accountEntity.getBalance(), account.getBalance());
        Assertions.assertEquals(accountEntity.getClientEntity().getClientId(), account.getClientId());
    }

    @Test
    void putMoneyPositiveTest() throws AccountException {
        final AccountEntity accountEntity = getAccountEntity();
        when(accountDao.findAccountById(anyLong())).thenReturn(Optional.of(accountEntity));
        when(accountDao.insertAccount(any())).thenReturn(accountEntity);

        final Account account = accountService.putMoney(2, BigDecimal.valueOf(1000));

        assertThat(account).isNotNull();
        Assertions.assertEquals(accountEntity.getId(), account.getId());
        Assertions.assertEquals(accountEntity.getBalance(), account.getBalance());
        Assertions.assertEquals(accountEntity.getClientEntity().getClientId(), account.getClientId());
    }

    @Test
    void putMoneyNegativeTest() {
        when(accountDao.findAccountById(anyLong())).thenReturn(Optional.of(getAccountEntity()));

        Assertions.assertThrows(NegativePutSumException.class, () -> accountService.putMoney(2, BigDecimal.valueOf(-1000)));
    }

    @Test
    void getMoneyPositiveTest() throws AccountException {
        final AccountEntity accountEntity = getAccountEntity();
        when(accountDao.findAccountById(anyLong())).thenReturn(Optional.of(accountEntity));
        when(accountDao.insertAccount(any())).thenReturn(accountEntity);

        final Account account = accountService.getMoney(2, BigDecimal.valueOf(1000));

        assertThat(account).isNotNull();
        Assertions.assertEquals(accountEntity.getId(), account.getId());
        Assertions.assertEquals(accountEntity.getBalance(), account.getBalance());
        Assertions.assertEquals(accountEntity.getClientEntity().getClientId(), account.getClientId());
    }

    @Test
    void getMoneyNegativeTest() {
        final AccountEntity accountEntity = getAccountEntity();
        when(accountDao.findAccountById(anyLong())).thenReturn(Optional.of(accountEntity));

        Assertions.assertThrows(NegativePutSumException.class, () -> accountService.getMoney(2, BigDecimal.valueOf(-1000)));
    }

    @Test
    void getNotEnoughMoneyNegativeTest() {
        final AccountEntity accountEntity = getAccountEntity();
        when(accountDao.findAccountById(anyLong())).thenReturn(Optional.of(accountEntity));

        Assertions.assertThrows(NotEnoughMoneyException.class, () -> accountService.getMoney(2, BigDecimal.valueOf(1500)));
    }

    @Test
    void findAccountById() throws AccountException {
        final AccountEntity accountEntity = getAccountEntity();
        when(accountDao.findAccountById(anyLong())).thenReturn(Optional.of(accountEntity));

        final Account account = accountService.findAccountById(1);

        assertThat(account).isNotNull();
        Assertions.assertEquals(accountEntity.getId(), account.getId());
        Assertions.assertEquals(accountEntity.getBalance(), account.getBalance());
        Assertions.assertEquals(accountEntity.getClientEntity().getClientId(), account.getClientId());
    }

    @Test
    void getAllClientAccountsTest() throws ClientNotFoundException {
        final ClientEntity clientEntity = getClientEntity();
        final List<AccountEntity> accountEntityList = getAccountEntityList();

        when(clientDao.getClientById(anyLong())).thenReturn(Optional.of(clientEntity));
        when(accountDao.getClientAccounts(any())).thenReturn(accountEntityList);

        final List<Account> allClientAccounts = accountService.getAllClientAccounts(1);

        assertThat(allClientAccounts).isNotNull();
        Assertions.assertEquals(accountEntityList.get(0).getId(), allClientAccounts.get(0).getId());
        Assertions.assertEquals(accountEntityList.get(0).getBalance(), allClientAccounts.get(0).getBalance());
        Assertions.assertEquals(accountEntityList.get(0).getClientEntity().getClientId(), allClientAccounts.get(0).getClientId());
        Assertions.assertEquals(accountEntityList.get(1).getId(), allClientAccounts.get(1).getId());
        Assertions.assertEquals(accountEntityList.get(1).getBalance(), allClientAccounts.get(1).getBalance());
        Assertions.assertEquals(accountEntityList.get(1).getClientEntity().getClientId(), allClientAccounts.get(1).getClientId());
    }

    @Test
    void getClientByAccountId() throws ClientNotFoundException, AccountException {
        when(accountDao.findAccountById(anyLong())).thenReturn(Optional.of(getAccountEntity()));
        final ClientEntity clientEntity = getClientEntity();
        when(clientDao.getClientById(anyLong())).thenReturn(Optional.of(clientEntity));

        final Client client = accountService.getClientByAccountId(2);

        assertThat(client).isNotNull();
        Assertions.assertEquals(clientEntity.getClientId(), client.getId());
        Assertions.assertEquals(clientEntity.getName(), client.getName());
        Assertions.assertEquals(clientEntity.getBirthDate(), client.getBirthDate());
        Assertions.assertEquals(clientEntity.getEmail(), client.getEmail());
    }

    @Test
    void deleteAccount() throws AccountException {
        when(accountDao.findAccountById(anyLong())).thenReturn(Optional.of(getAccountEntity()));

        accountService.deleteAccount(2);

        verify(accountDao, times(1)).deleteAccount(2L);
    }
}