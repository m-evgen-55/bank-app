package com.demo.bankapp.service.validation.stgategy.salary;

import com.demo.bankapp.controller.request.LoanRequest;
import com.demo.bankapp.exception.loan.NotEnoughIncomeException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class SalaryValidationForLoan implements SalaryValidationStrategy {

    @Override
    public void validate(
            final LoanRequest loanRequest,
            final BigDecimal monthPaymentForLoan
    ) throws NotEnoughIncomeException {

        if ((loanRequest.getMonthSalary().divide(monthPaymentForLoan, RoundingMode.CEILING)).intValue() < 3) {
            throw new NotEnoughIncomeException("The loan was not approved for the client. The income presented by the client is not enough.");
        }
    }
}
