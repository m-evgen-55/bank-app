package com.demo.bankapp.service;

import com.demo.bankapp.controller.request.LoanRequest;
import com.demo.bankapp.dao.ClientDao;
import com.demo.bankapp.dao.LoanDao;
import com.demo.bankapp.exception.client.ClientException;
import com.demo.bankapp.exception.client.ClientNotFoundException;
import com.demo.bankapp.exception.loan.*;
import com.demo.bankapp.model.Client;
import com.demo.bankapp.model.Loan;
import com.demo.bankapp.model.entity.ClientEntity;
import com.demo.bankapp.model.entity.LoanEntity;
import com.demo.bankapp.service.validation.ClientValidationServiceImpl;
import com.demo.bankapp.service.validation.SalaryValidationServiceImpl;
import com.demo.bankapp.service.validation.stgategy.client.LoanClientAgeClientValidation;
import com.demo.bankapp.service.validation.stgategy.salary.SalaryValidationForLoan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.demo.bankapp.test_data.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoanServiceImplTest {

    private LoanDao loanDao;
    private ClientDao clientDao;
    private LoanService loanService;

    @BeforeEach
    public void setUp() {
        loanDao = mock(LoanDao.class);
        clientDao = mock(ClientDao.class);
        SalaryValidationForLoan salaryValidationForLoan = new SalaryValidationForLoan();
        loanService = new LoanServiceImpl(
                loanDao,
                clientDao,
                new ClientValidationServiceImpl(),
                new SalaryValidationServiceImpl(),
                new LoanClientAgeClientValidation(),
                salaryValidationForLoan);
    }

    @Test
    void addNewLoanPositiveTest() throws LoanException, ClientException {
        when(clientDao.getClientById(anyLong())).thenReturn(Optional.of(getClientEntity()));
        final LoanEntity loanEntity = getLoanEntity();
        when(loanDao.insertLoan(any())).thenReturn(loanEntity);

        final Loan loan = loanService.addNewLoan(getLoanRequest());

        assertThat(loan).isNotNull();
        Assertions.assertEquals(loanEntity.getId(), loan.getId());
        Assertions.assertEquals(loanEntity.getSum(), loan.getSum());
        Assertions.assertEquals(loanEntity.getRate(), loan.getRate());
        Assertions.assertEquals(loanEntity.getOverpayment(), loan.getOverpayment());
        Assertions.assertEquals(loanEntity.getDuration(), loan.getDuration());
        Assertions.assertEquals(loanEntity.getToBeReturnedSum(), loan.getToBeReturnedSum());
        Assertions.assertEquals(loanEntity.getAlreadyReturnedSum(), loan.getAlreadyReturnedSum());
        Assertions.assertEquals(loanEntity.getClientEntity().getClientId(), loan.getClientId());
        Assertions.assertEquals(loanEntity.isPaidOff(), loan.isPaidOff());
    }

    @Test
    void addNewLoanClientAgeNegativeTest() {
        final ClientEntity clientEntity = getClientEntity();
        clientEntity.setBirthDate(LocalDate.of(1950, 4, 25));
        when(clientDao.getClientById(anyLong())).thenReturn(Optional.of(clientEntity));

        Assertions.assertThrows(AgeExceededException.class, () -> loanService.addNewLoan(getLoanRequest()));
    }

    @Test
    void addNewLoanClientSalaryNegativeTest() {
        final ClientEntity clientEntity = getClientEntity();
        when(clientDao.getClientById(anyLong())).thenReturn(Optional.of(clientEntity));
        final LoanRequest loanRequest = getLoanRequest();
        loanRequest.setLoanSum(BigDecimal.valueOf(15000));
        loanRequest.setMonthSalary(BigDecimal.valueOf(1000));

        Assertions.assertThrows(NotEnoughIncomeException.class, () -> loanService.addNewLoan(loanRequest));
    }

    @Test
    void payForLoanPositiveTest() throws LoanException {
        final LoanEntity loanEntity = getLoanEntity();
        when(loanDao.findLoanById(anyLong())).thenReturn(Optional.of(loanEntity));
        when(loanDao.insertLoan(any())).thenReturn(loanEntity);

        final Loan loan = loanService.payForLoan(3, BigDecimal.valueOf(100));

        assertThat(loan).isNotNull();
        Assertions.assertEquals(loanEntity.getId(), loan.getId());
        Assertions.assertEquals(loanEntity.getSum(), loan.getSum());
        Assertions.assertEquals(loanEntity.getRate(), loan.getRate());
        Assertions.assertEquals(loanEntity.getOverpayment(), loan.getOverpayment());
        Assertions.assertEquals(loanEntity.getDuration(), loan.getDuration());
        Assertions.assertEquals(loanEntity.getToBeReturnedSum(), loan.getToBeReturnedSum());
        Assertions.assertEquals(loanEntity.getAlreadyReturnedSum(), loan.getAlreadyReturnedSum());
        Assertions.assertEquals(loanEntity.getClientEntity().getClientId(), loan.getClientId());
        Assertions.assertEquals(loanEntity.isPaidOff(), loan.isPaidOff());
    }

    @Test
    void payForLoanNegativeSumTest() {
        Assertions.assertThrows(NegativePaySumException.class, () -> loanService.payForLoan(3, BigDecimal.valueOf(-100)));
    }

    @Test
    void payForLoanAlreadyPaidOffNegativeTest() {
        final LoanEntity loanEntity = getLoanEntity();
        loanEntity.setPaidOff(true);
        when(loanDao.findLoanById(anyLong())).thenReturn(Optional.of(loanEntity));

        Assertions.assertThrows(LoanAlreadyRepaidException.class, () -> loanService.payForLoan(3, BigDecimal.valueOf(100)));
    }

    @Test
    void getClientByLoanIdTest() throws ClientNotFoundException, LoanException {
        when(loanDao.findLoanById(anyLong())).thenReturn(Optional.of(getLoanEntity()));
        final ClientEntity clientEntity = getClientEntity();
        when(clientDao.getClientById(anyLong())).thenReturn(Optional.of(clientEntity));

        final Client client = loanService.getClientByLoanId(3);

        assertThat(client).isNotNull();
        Assertions.assertEquals(clientEntity.getClientId(), client.getId());
        Assertions.assertEquals(clientEntity.getName(), client.getName());
        Assertions.assertEquals(clientEntity.getEmail(), client.getEmail());
        Assertions.assertEquals(clientEntity.getBirthDate(), client.getBirthDate());
    }

    @Test
    void getLoanByIdTest() throws LoanException {
        final LoanEntity loanEntity = getLoanEntity();
        when(loanDao.findLoanById(anyLong())).thenReturn(Optional.of(loanEntity));

        final Loan loan = loanService.getLoanById(3);

        assertThat(loan).isNotNull();
        Assertions.assertEquals(loanEntity.getId(), loan.getId());
        Assertions.assertEquals(loanEntity.getSum(), loan.getSum());
        Assertions.assertEquals(loanEntity.getRate(), loan.getRate());
        Assertions.assertEquals(loanEntity.getOverpayment(), loan.getOverpayment());
        Assertions.assertEquals(loanEntity.getDuration(), loan.getDuration());
        Assertions.assertEquals(loanEntity.getToBeReturnedSum(), loan.getToBeReturnedSum());
        Assertions.assertEquals(loanEntity.getAlreadyReturnedSum(), loan.getAlreadyReturnedSum());
        Assertions.assertEquals(loanEntity.getClientEntity().getClientId(), loan.getClientId());
        Assertions.assertEquals(loanEntity.isPaidOff(), loan.isPaidOff());
    }

    @Test
    void getClientLoansTest() throws ClientException {
        final ClientEntity clientEntity = getClientEntity();
        final List<LoanEntity> loanEntityList = getLoanEntityList();
        clientEntity.setLoans(loanEntityList);
        when(clientDao.getClientById(anyLong())).thenReturn(Optional.of(clientEntity));

        final List<Loan> loanList = loanService.getClientLoans(1);

        assertThat(loanList).isNotNull();
        Assertions.assertEquals(loanEntityList.get(0).getId(), loanList.get(0).getId());
        Assertions.assertEquals(loanEntityList.get(0).getSum(), loanList.get(0).getSum());
        Assertions.assertEquals(loanEntityList.get(0).getRate(), loanList.get(0).getRate());
        Assertions.assertEquals(loanEntityList.get(0).getOverpayment(), loanList.get(0).getOverpayment());
        Assertions.assertEquals(loanEntityList.get(0).getDuration(), loanList.get(0).getDuration());
        Assertions.assertEquals(loanEntityList.get(0).getToBeReturnedSum(), loanList.get(0).getToBeReturnedSum());
        Assertions.assertEquals(loanEntityList.get(0).getAlreadyReturnedSum(), loanList.get(0).getAlreadyReturnedSum());
        Assertions.assertEquals(loanEntityList.get(0).getClientEntity().getClientId(), loanList.get(0).getClientId());
        Assertions.assertEquals(loanEntityList.get(0).isPaidOff(), loanList.get(0).isPaidOff());
    }
}