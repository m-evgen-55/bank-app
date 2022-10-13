package com.demo.bankapp.service.validation.stgategy.salary;

import com.demo.bankapp.controller.request.LoanRequest;
import com.demo.bankapp.exception.loan.NotEnoughIncomeException;

import java.math.BigDecimal;

public interface SalaryValidationStrategy {

    void validate(LoanRequest loanRequest, BigDecimal monthPaymentForLoan) throws NotEnoughIncomeException;
}
