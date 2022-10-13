package com.demo.bankapp.service;

import com.demo.bankapp.controller.request.LoanRequest;
import com.demo.bankapp.dao.ClientDao;
import com.demo.bankapp.dao.LoanDao;
import com.demo.bankapp.exception.client.ClientException;
import com.demo.bankapp.exception.client.ClientNotFoundException;
import com.demo.bankapp.exception.loan.LoanAlreadyRepaidException;
import com.demo.bankapp.exception.loan.LoanException;
import com.demo.bankapp.exception.loan.LoanNotFoundException;
import com.demo.bankapp.exception.loan.NegativePaySumException;
import com.demo.bankapp.model.Client;
import com.demo.bankapp.model.Loan;
import com.demo.bankapp.model.entity.ClientEntity;
import com.demo.bankapp.model.entity.LoanEntity;
import com.demo.bankapp.service.validation.ClientValidationService;
import com.demo.bankapp.service.validation.SalaryValidationService;
import com.demo.bankapp.service.validation.stgategy.client.LoanClientAgeClientValidation;
import com.demo.bankapp.service.validation.stgategy.salary.SalaryValidationForLoan;
import com.demo.bankapp.utils.LoanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.demo.bankapp.utils.ClientUtils.mapClientEntityToClient;
import static com.demo.bankapp.utils.LoanUtils.mapLoanEntityToLoan;
import static com.demo.bankapp.utils.LoanUtils.mapLoanRequestToLoanEntity;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    // TODO вынести в properties
    private final BigDecimal YEAR_LOAN_COEFFICIENT = BigDecimal.valueOf(1.1);

    private final LoanDao loanDao;
    private final ClientDao clientDao;
    private final ClientValidationService clientValidationService;
    private final SalaryValidationService salaryValidationService;
    private final LoanClientAgeClientValidation loanClientAgeClientValidation;
    private final SalaryValidationForLoan salaryValidationForLoan;

    // To be approved for a loan, the client must meet the following conditions:
    // - be under 50 years old
    // - the monthly payment on the loan, including interest, must be less than his monthly salary
    @Override
    public Loan addNewLoan(final LoanRequest loanRequest) throws LoanException, ClientException {
        final ClientEntity clientEntity = clientDao.getClientById(loanRequest.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client id = %s not found", loanRequest.getClientId())));

        clientValidationService.validate(Collections
                .singletonList(loanClientAgeClientValidation), clientEntity);
        salaryValidationService.validate(Collections
                .singletonList(salaryValidationForLoan), loanRequest, getMonthPaymentForLoan(loanRequest));

        final int loanRate = calculateLoanRate();

        return mapLoanEntityToLoan(
                // TODO realize the possibility of obtaining a loan for a smaller amount and term from the requested
                loanDao.insertLoan(
                        mapLoanRequestToLoanEntity(loanRequest, loanRate, calculateOverpayment(loanRequest, loanRate), clientEntity))
        );
    }

    // the method checks whether the loan is repaid. Unable to make a payment on a repaid loan
    @Override
    public Loan payForLoan(final long loanId, final BigDecimal paySum) throws LoanException {
        if (paySum.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new NegativePaySumException("The deposited amount is negative. Please enter a positive number.");
        }

        final LoanEntity loanEntity = loanDao.findLoanById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(String.format("Loan id = %s not found.", loanId)));

        if (loanEntity.isPaidOff()) {
            throw new LoanAlreadyRepaidException(String.format("Loan id = %s is already repaid.", loanId));
        }

        loanEntity.setAlreadyReturnedSum(loanEntity.getAlreadyReturnedSum().add(paySum));
        calculateRepaidLoan(loanEntity);
        return mapLoanEntityToLoan(loanDao.insertLoan(loanEntity));
    }

    @Override
    public Client getClientByLoanId(final long loanId) throws LoanNotFoundException, ClientNotFoundException {
        final LoanEntity loanEntity = loanDao.findLoanById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(String.format("Loan id = %s not found.", loanId)));

        final ClientEntity clientEntity = clientDao.getClientById(loanEntity.getClientEntity().getClientId())
                .orElseThrow(() -> new ClientNotFoundException(String
                        .format("Client id = %s not found", loanEntity.getClientEntity().getClientId())));

        return mapClientEntityToClient(clientEntity);
    }

    @Override
    public Loan getLoanById(long loanId) throws LoanException {
        final LoanEntity loanEntity = loanDao.findLoanById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(String.format("Loan id = %s not found.", loanId)));

        return mapLoanEntityToLoan(loanEntity);
    }

    @Override
    public List<Loan> getClientLoans(long clientId) throws ClientException {
        final ClientEntity clientEntity = clientDao.getClientById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client id = %s not found", clientId)));

        return clientEntity.getLoans()
                .stream()
                .map(LoanUtils::mapLoanEntityToLoan)
                .collect(Collectors.toList());
    }

    // TODO add logic for calculating different loan rates depending on the characteristics of the client
    private int calculateLoanRate() {
        return YEAR_LOAN_COEFFICIENT.multiply(BigDecimal.valueOf(100)).
                subtract(BigDecimal.valueOf(100)).toBigInteger().shortValueExact();
    }

    private BigDecimal getMonthPaymentForLoan(final LoanRequest loanRequest) {
        final BigDecimal loanTime = BigDecimal.valueOf(loanRequest.getLoanTimeInMonth());
        final BigDecimal monthPaymentForLoanWithoutProcent =
                loanRequest.getLoanSum().divide(loanTime, 5, RoundingMode.CEILING);
        return monthPaymentForLoanWithoutProcent.multiply(YEAR_LOAN_COEFFICIENT);
    }

    private void calculateRepaidLoan(final LoanEntity loanEntity) {
        if (loanEntity.getSum().add(loanEntity.getToBeReturnedSum()).compareTo(loanEntity.getAlreadyReturnedSum()) <= 0) {
            loanEntity.setPaidOff(true);
        }
    }

    // approximate calculation
    private BigDecimal calculateOverpayment(final LoanRequest loanRequest, final int loanRate) {
        return loanRequest.getLoanSum().divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(loanRate))
                .multiply(BigDecimal.valueOf(loanRequest.getLoanTimeInMonth() / 12));
    }
}
