package com.demo.bankapp.exception.loan;

public class NegativePaySumException extends LoanException {

    public NegativePaySumException(String message) {
        super(message);
    }
}
