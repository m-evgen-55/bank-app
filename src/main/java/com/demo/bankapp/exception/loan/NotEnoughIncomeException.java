package com.demo.bankapp.exception.loan;

public class NotEnoughIncomeException extends LoanException {

    public NotEnoughIncomeException(String message) {
        super(message);
    }
}
