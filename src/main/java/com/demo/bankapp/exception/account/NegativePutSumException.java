package com.demo.bankapp.exception.account;

public class NegativePutSumException extends AccountException {

    public NegativePutSumException(String message) {
        super(message);
    }
}
