package com.demo.bankapp.test_data;

import com.demo.bankapp.controller.request.ClientRequest;
import com.demo.bankapp.controller.request.LoanRequest;
import com.demo.bankapp.model.Account;
import com.demo.bankapp.model.Client;
import com.demo.bankapp.model.Loan;
import com.demo.bankapp.model.entity.AccountEntity;
import com.demo.bankapp.model.entity.ClientEntity;
import com.demo.bankapp.model.entity.LoanEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TestData {

    public static Client getClient() {
        return new Client()
                .setId(1)
                .setName("Ivanov Ivan")
                .setBirthDate(LocalDate.of(1980, 4, 25))
                .setEmail("ivanov@yandex.ru");
    }

    public static ClientRequest getClientRequest() {
        return new ClientRequest()
                .setName("Ivanov Ivan")
                .setBirthDate(LocalDate.of(1980, 4, 25))
                .setEmail("ivanov@yandex.ru");
    }

    public static ClientEntity getClientEntity() {
        return new ClientEntity()
                .setClientId(1)
                .setName("Ivanov Ivan")
                .setBirthDate(LocalDate.of(1980, 4, 25))
                .setEmail("ivanov@mail.ru");
    }

    public static Account getAccount() {
        return new Account()
                .setId(2)
                .setBalance(BigDecimal.valueOf(1000))
                .setClientId(1);
    }

    public static List<Account> getAccountList() {
        return List.of(
                new Account()
                        .setId(2)
                        .setBalance(BigDecimal.valueOf(1000))
                        .setClientId(1),
                new Account()
                        .setId(3)
                        .setBalance(BigDecimal.valueOf(1200))
                        .setClientId(1)
        );
    }

    public static AccountEntity getAccountEntity() {
        return new AccountEntity()
                .setId(2)
                .setBalance(BigDecimal.valueOf(1000))
                .setClientEntity(getClientEntity());
    }

    public static List<AccountEntity> getAccountEntityList() {
        return List.of(
                new AccountEntity()
                        .setId(2)
                        .setBalance(BigDecimal.valueOf(1000))
                        .setClientEntity(getClientEntity()),
                new AccountEntity()
                        .setId(3)
                        .setBalance(BigDecimal.valueOf(1200))
                        .setClientEntity(getClientEntity())
        );
    }

    public static Loan getLoan() {
        return new Loan()
                .setId(5)
                .setSum(BigDecimal.valueOf(2000))
                .setRate(9)
                .setOverpayment(BigDecimal.valueOf(360))
                .setDuration(24)
                .setToBeReturnedSum(BigDecimal.valueOf(2360))
                .setAlreadyReturnedSum(BigDecimal.valueOf(2360))
                .setClientId(1)
                .setPaidOff(true);
    }

    public static LoanRequest getLoanRequest() {
        return new LoanRequest()
                .setClientId(1)
                .setLoanSum(BigDecimal.valueOf(2000))
                .setLoanTimeInMonth(24)
                .setMonthSalary(BigDecimal.valueOf(10000));
    }

    public static LoanEntity getLoanEntity() {
        return new LoanEntity()
                .setId(3)
                .setSum(BigDecimal.valueOf(1000))
                .setRate(10)
                .setOverpayment(BigDecimal.valueOf(100))
                .setDuration(12)
                .setToBeReturnedSum(BigDecimal.valueOf(1100))
                .setAlreadyReturnedSum(BigDecimal.valueOf(500))
                .setClientEntity(getClientEntity())
                .setPaidOff(false);
    }

    public static List<Loan> getLoanList() {
        return List.of(
                new Loan()
                        .setId(4)
                        .setSum(BigDecimal.valueOf(1500))
                        .setRate(10)
                        .setOverpayment(BigDecimal.valueOf(150))
                        .setDuration(12)
                        .setToBeReturnedSum(BigDecimal.valueOf(1650))
                        .setAlreadyReturnedSum(BigDecimal.valueOf(500))
                        .setClientId(1)
                        .setPaidOff(false),
                new Loan()
                        .setId(5)
                        .setSum(BigDecimal.valueOf(2000))
                        .setRate(9)
                        .setOverpayment(BigDecimal.valueOf(360))
                        .setDuration(24)
                        .setToBeReturnedSum(BigDecimal.valueOf(2360))
                        .setAlreadyReturnedSum(BigDecimal.valueOf(2360))
                        .setClientId(1)
                        .setPaidOff(true)
        );
    }

    public static List<LoanEntity> getLoanEntityList() {
        return List.of(
                new LoanEntity()
                        .setId(4)
                        .setSum(BigDecimal.valueOf(1500))
                        .setRate(10)
                        .setOverpayment(BigDecimal.valueOf(150))
                        .setDuration(12)
                        .setToBeReturnedSum(BigDecimal.valueOf(1650))
                        .setAlreadyReturnedSum(BigDecimal.valueOf(500))
                        .setClientEntity(getClientEntity())
                        .setPaidOff(false),
                new LoanEntity()
                        .setId(5)
                        .setSum(BigDecimal.valueOf(2000))
                        .setRate(9)
                        .setOverpayment(BigDecimal.valueOf(360))
                        .setDuration(24)
                        .setToBeReturnedSum(BigDecimal.valueOf(2360))
                        .setAlreadyReturnedSum(BigDecimal.valueOf(2360))
                        .setClientEntity(getClientEntity())
                        .setPaidOff(true)
        );
    }
}
