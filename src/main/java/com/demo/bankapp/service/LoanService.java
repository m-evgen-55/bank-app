package com.demo.bankapp.service;

import com.demo.bankapp.controller.request.LoanRequest;
import com.demo.bankapp.exception.client.ClientException;
import com.demo.bankapp.exception.client.ClientNotFoundException;
import com.demo.bankapp.exception.loan.LoanException;
import com.demo.bankapp.model.Client;
import com.demo.bankapp.model.Loan;

import java.math.BigDecimal;
import java.util.List;

public interface LoanService {

    Loan addNewLoan(LoanRequest loanRequest) throws LoanException, ClientException;

    Loan payForLoan(long loanId, BigDecimal paySum) throws LoanException;

    Client getClientByLoanId(long loanId) throws LoanException, ClientNotFoundException;

    Loan getLoanById(long loanId) throws LoanException;

    List<Loan> getClientLoans(long clientId) throws ClientException;
}
