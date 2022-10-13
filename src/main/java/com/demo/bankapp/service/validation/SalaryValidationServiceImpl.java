package com.demo.bankapp.service.validation;

import com.demo.bankapp.controller.request.LoanRequest;
import com.demo.bankapp.exception.loan.NotEnoughIncomeException;
import com.demo.bankapp.service.validation.stgategy.salary.SalaryValidationStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SalaryValidationServiceImpl implements SalaryValidationService {

    @Override
    public void validate(
            final List<SalaryValidationStrategy> salaryValidationStrategyList,
            final LoanRequest loanRequest,
            final BigDecimal monthPaymentForLoan
    ) throws NotEnoughIncomeException {
        for (SalaryValidationStrategy validationStrategy : salaryValidationStrategyList) {
            validationStrategy.validate(loanRequest, monthPaymentForLoan);
        }
    }
}
