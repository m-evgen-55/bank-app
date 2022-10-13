package com.demo.bankapp.exception.account;

public class NotEnoughMoneyException extends AccountException {

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
