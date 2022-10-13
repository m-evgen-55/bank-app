package com.demo.bankapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class Account {

    private long id;
    private BigDecimal balance;
    private long clientId;
}