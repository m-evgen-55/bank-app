package com.demo.bankapp.utils;

import com.demo.bankapp.controller.request.LoanRequest;
import com.demo.bankapp.model.Loan;
import com.demo.bankapp.model.entity.ClientEntity;
import com.demo.bankapp.model.entity.LoanEntity;

import java.math.BigDecimal;

public final class LoanUtils {

    private LoanUtils() {
    }

    public static Loan mapLoanEntityToLoan(final LoanEntity loanEntity) {
        return new Loan()
                .setId(loanEntity.getId())
                .setSum(loanEntity.getSum())
                .setRate(loanEntity.getRate())
                .setOverpayment(loanEntity.getOverpayment())
                .setDuration(loanEntity.getDuration())
                .setToBeReturnedSum(loanEntity.getToBeReturnedSum())
                .setAlreadyReturnedSum(loanEntity.getAlreadyReturnedSum())
                .setClientId(loanEntity.getClientEntity().getClientId())
                .setPaidOff(loanEntity.isPaidOff());
    }

    public static LoanEntity mapLoanRequestToLoanEntity(
            final LoanRequest loanRequest,
            final int loanRate,
            final BigDecimal overpayment,
            final ClientEntity clientEntity
    ) {
        return new LoanEntity()
                .setClientEntity(clientEntity)
                .setRate(loanRate)
                .setOverpayment(overpayment)
                .setDuration(loanRequest.getLoanTimeInMonth())
                .setToBeReturnedSum(loanRequest.getLoanSum().add(overpayment))
                .setAlreadyReturnedSum(BigDecimal.valueOf(0))
                .setSum(loanRequest.getLoanSum())
                .setPaidOff(false);
    }
}
