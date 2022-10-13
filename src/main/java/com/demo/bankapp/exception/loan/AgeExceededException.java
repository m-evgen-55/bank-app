package com.demo.bankapp.exception.loan;

public class AgeExceededException extends LoanException {

    public AgeExceededException(String message) {
        super(message);
    }
}
