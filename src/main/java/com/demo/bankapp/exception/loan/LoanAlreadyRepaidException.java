package com.demo.bankapp.exception.loan;

public class LoanAlreadyRepaidException extends LoanException {

    public LoanAlreadyRepaidException(String message) {
        super(message);
    }
}
