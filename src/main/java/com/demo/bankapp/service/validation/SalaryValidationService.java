package com.demo.bankapp.service.validation;

import com.demo.bankapp.controller.request.LoanRequest;
import com.demo.bankapp.exception.loan.NotEnoughIncomeException;
import com.demo.bankapp.service.validation.stgategy.salary.SalaryValidationStrategy;

import java.math.BigDecimal;
import java.util.List;

public interface SalaryValidationService {

    void validate(
            List<SalaryValidationStrategy> salaryValidationStrategyList,
            LoanRequest loanRequest,
            BigDecimal monthPaymentForLoan) throws NotEnoughIncomeException;
}
